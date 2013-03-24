package db;

import exception.BusinessException;
import exception.DAOException;
import exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 18.02.13
 * Time: 9:14
 * To change this template use File | Settings | File Templates.
 */
public class AllScriptSInDirectoryRunner {
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("configure");
    private static AllScriptSInDirectoryRunner instance;
    private String directoryPath;
    private List<String> launchedScripts = null;
    private File storage;
    private static Logger logger;
    private boolean whetherToScanForLaunchedScripts;
    private LaunchedScriptNamesStorage launched;

    private AllScriptSInDirectoryRunner(boolean whetherToScanForLaunchedScripts) throws IOException, SAXException, XPathExpressionException, ParserConfigurationException, TransformerException {
        logger = LoggerFactory.getLogger(this.getClass());
        //todo smth with this
        this.directoryPath = resourceBundle.getString("scripts");
        this.storage = new File(resourceBundle.getString("launchedScriptsNamesStorage"));
        if (!storage.exists()) storage.createNewFile();
        this.whetherToScanForLaunchedScripts = whetherToScanForLaunchedScripts;
        this.launched = (whetherToScanForLaunchedScripts ? LaunchedScriptNamesStorage.getInstance(storage) : null);
    }

    public static AllScriptSInDirectoryRunner getInstance(boolean whetherToScanForLaunchedScripts) throws IOException, SAXException, XPathExpressionException, ParserConfigurationException, TransformerException {
        instance = new AllScriptSInDirectoryRunner(whetherToScanForLaunchedScripts);
        return instance;
    }

    public void run(){
        Connection conn = null;
        try {
        conn = getConnection();
        ScriptRunner sr = new ScriptRunner(conn, logger, true, false);
        runScripts(sr);
        }
        catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        } finally {
            try {
                if ( !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                logger.error(e.getLocalizedMessage());
            }
        }
    }

    private List getFilesInDirectory() throws URISyntaxException {
        String item;
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles();
        List files = new ArrayList();

        for (int i = 0; i < listOfFiles.length; i++) {
            item = listOfFiles[i].getName();
            if (listOfFiles[i].isFile() & item.toLowerCase().endsWith(".sql")) {
                files.add(item);
            }
        }
        return files;
    }


    public void runScripts(ScriptRunner scriptRunner) throws URISyntaxException {
        storage.setWritable(true);
        List<String> scriptsNames = null;
        scriptsNames = getFilesInDirectory();
        try {
            Reader reader;

            for (String script : scriptsNames) {
                reader = new BufferedReader(
                        new FileReader(directoryPath + script));

                if (!whetherToScanForLaunchedScripts) {
                    scriptRunner.runScript(reader);
                } else if (!isWasAlreadyRunning(script)) {
                    scriptRunner.runScript(reader);
                    launched.writeLaunchedScriptNamesInXml(script);
                }
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        } finally {
            storage.setWritable(false);
        }
    }


    private boolean isWasAlreadyRunning(String script) throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        launchedScripts = launched.getLaunchedScripts();
        if (launchedScripts == null) {
            return false;
        } else {
            return launchedScripts.contains(script);
        }
    }

    private Connection getConnection() throws SystemException, BusinessException {
        ResourceBundle resource =
                ResourceBundle.getBundle("database");
        String url = resource.getString("url");
        String driver = resource.getString("driver");
        String user = resource.getString("user");
        String pass = resource.getString("password");
        try {
            Class.forName(driver).newInstance();
            return DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            throw new SystemException("Driver was not found!", e);
        } catch (InstantiationException e) {
            throw new SystemException("Specified class object cannot be instantiated" +
                    " because it is an interface or is an abstract class", e);
        } catch (IllegalAccessException e) {
            throw new SystemException("Currently executing method does not have access" +
                    " to the definition of the specified class, field, method or constructor", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


}
