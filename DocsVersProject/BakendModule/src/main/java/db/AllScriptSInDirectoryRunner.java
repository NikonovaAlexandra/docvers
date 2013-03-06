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

    private static AllScriptSInDirectoryRunner instance;
    private String directoryPath;
    private List<String> launchedScripts = null;
    private File storage;
    private static Logger logger = LoggerFactory.getLogger(AllScriptSInDirectoryRunner.class);
    private boolean whetherToScanForLaunchedScripts;
    private LaunchedScriptNamesStorage launched;

    private AllScriptSInDirectoryRunner(String directoryPath, boolean whetherToScanForLaunchedScripts) throws IOException, SAXException, XPathExpressionException, ParserConfigurationException, TransformerException {
        this.directoryPath = directoryPath;
      // TODO : on other system does not work
        this.storage = new File("C:\\Documents and Settings\\Admin\\Рабочий стол\\docvers\\trunk\\DocsVersProject\\BakendModule\\src\\main\\java\\db\\launched.xml");
        storage.createNewFile();
        //storage.setReadOnly();
        this.whetherToScanForLaunchedScripts = whetherToScanForLaunchedScripts;
        this.launched = (whetherToScanForLaunchedScripts ? LaunchedScriptNamesStorage.getInstance(storage) : null);
    }

    public static AllScriptSInDirectoryRunner getInstance(String directoryPath, boolean whetherToScanForLaunchedScripts) throws IOException, SAXException, XPathExpressionException, ParserConfigurationException, TransformerException {
        instance = new AllScriptSInDirectoryRunner(directoryPath, whetherToScanForLaunchedScripts);
        return instance;
    }

    public static void main(String[] args) throws IOException, SAXException, XPathExpressionException, ParserConfigurationException, BusinessException, SystemException, URISyntaxException, TransformerException {
        AllScriptSInDirectoryRunner instatnce = getInstance("C:\\Documents and Settings\\Admin\\Рабочий стол\\docvers\\trunk\\DocsVersProject\\BakendModule\\src\\main\\resources\\scripts\\", true);
        Connection conn = instatnce.getConnection();
        ScriptRunner sr = new ScriptRunner(conn, true, false);
        instatnce.runScripts(sr);
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
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TransformerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (XPathExpressionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
