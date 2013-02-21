package db;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 18.02.13
 * Time: 9:46
 * To change this template use File | Settings | File Templates.
 */
public class LaunchedScriptNamesStorage {
    private static List scripts;
    private static LaunchedScriptNamesStorage instance;
    private static Document document;

    private LaunchedScriptNamesStorage(String name) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        scripts = new ArrayList();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        File f = new File(name);
        document = builder.parse(f);
        parseScripts();
    }

    public static LaunchedScriptNamesStorage getInstance(String name) throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {
        if(null == instance){
            instance = new LaunchedScriptNamesStorage(name);
        }
        return instance;
    }

    public List getLaunchedScripts(){
        return scripts;
    }
    private  void parseScripts() throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        NodeList list = document.getFirstChild().getChildNodes();
        XPath xpath = XPathFactory.newInstance().newXPath();

        XPathExpression ex1;

        String pattern1;
        for(int i = 0 ; i < list.getLength() ; i++){

            pattern1 = "//scripts//script[" + i + "]//name";
            ex1 = xpath.compile(pattern1);
            String str = String.valueOf(ex1.evaluate(document, XPathConstants.STRING));
            if(!str.isEmpty()) {
                scripts.add(str);
            }

        }


    }
    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        return node.getNodeValue();
    }

}
