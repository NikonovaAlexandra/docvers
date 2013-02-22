package db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
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
    private String path;
    private List<String> runnedScripts = null;
    private File storage;
    private static Logger logger = LoggerFactory.getLogger(AllScriptSInDirectoryRunner.class);

    private AllScriptSInDirectoryRunner(String path, File storage) {
        this.path = path;
        this.storage = storage;
    }

    public static AllScriptSInDirectoryRunner getInstance(String path, File storage) {
        if (null == instance) {
            instance = new AllScriptSInDirectoryRunner(path, storage);
        }
        return instance;
    }

//    public static void main(String[] args) {
//        AllScriptSInDirectoryRunner.getInstance("C:\\Documents and Settings\\alni\\Desktop\\project\\docvers-master\\docvers\\trunk\\DocsVersProject\\BakendModule\\src\\main\\resources\\scripts\\").runScripts();
//    }

    private List getFilesInDirectory() {
        String item;
        File folder = new File(path);
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

    private void writeLaunchedScriptNamesInXml(String name) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        //Create the documentBuilderFactory
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        //Create the documentBuilder
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        //Create the Document  by parsing the file
        Document document = documentBuilder.parse(storage);
        //Get the root element of the xml Document;
        Element documentElement = document.getDocumentElement();
        //Get childNodes of the rootElement
        NodeList l = documentElement.getChildNodes();
        //Create a Node element
        Element node = document.createElement("script");
        Element nameNode = document.createElement("name");
        Text value = document.createTextNode(name);
        nameNode.setTextContent(name);
        //append Node to rootNode element
        node.appendChild(nameNode);
        documentElement.appendChild(node);
        document.replaceChild(documentElement, documentElement);
        Transformer tFormer =
                TransformerFactory.newInstance().newTransformer();
        //  Set output file to xml
        tFormer.setOutputProperty(OutputKeys.METHOD, "xml");
        //  Write the document back to the file
        Source source = new DOMSource(document);
        Result result = new StreamResult(storage);
        tFormer.transform(source, result);


    }

    public void runScripts() {
        List<String> scriptsNames = getFilesInDirectory();
        Connection conn = null;
        try {
            conn = getConnection();
            ScriptRunner sr = new ScriptRunner(conn, false, false);
            Reader reader;

            for (String script : scriptsNames) {
                reader = new BufferedReader(
                        new FileReader(path + script));

                if (!isWasAlreadyRunning(script)) {
                    sr.runScript(reader);
                    writeLaunchedScriptNamesInXml(script);
                }
            }
            conn.commit();
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
        }
    }


    private boolean isWasAlreadyRunning(String script) throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        runnedScripts = LaunchedScriptNamesStorage.getInstance(storage).getLaunchedScripts();
        if (runnedScripts == null) {
            return false;
        } else {
            return runnedScripts.contains(script);
        }
    }

    private Connection getConnection() throws SQLException {
        ResourceBundle resource =
                ResourceBundle.getBundle("database");
        String url = resource.getString("url");
        String driver = resource.getString("driver");
        String user = resource.getString("user");
        String pass = resource.getString("password");
        try {
            Class.forName(driver).newInstance();
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver isn't loaded!");
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        }
        return DriverManager.getConnection(url, user, pass);
    }


}
