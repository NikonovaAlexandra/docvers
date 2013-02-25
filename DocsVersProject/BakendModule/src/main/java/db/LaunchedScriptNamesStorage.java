package db;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
    private static Document document = null;

    private LaunchedScriptNamesStorage(File storage) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException, TransformerException {
        scripts = new ArrayList();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        if(storage.length() == 0) {
            document = builder.newDocument();
            Element rootElement = document.createElement("scripts");
            document.appendChild(rootElement);
            Element element = document.createElement("script");
            element.appendChild(document.createTextNode(""));
            rootElement.appendChild(element);
            document.appendChild(element);
            Transformer tFormer =
                    TransformerFactory.newInstance().newTransformer();
            //  Set output file to xml
            tFormer.setOutputProperty(OutputKeys.METHOD, "xml");
            //  Write the document back to the file
            Source source = new DOMSource(document);
            Result result = new StreamResult(storage);
            tFormer.transform(source, result);
        } else{
            document = builder.parse(storage);
        }
        parseScripts();
    }

    public static LaunchedScriptNamesStorage getInstance(File storage) throws XPathExpressionException, IOException, SAXException, ParserConfigurationException, TransformerException {
        instance = new LaunchedScriptNamesStorage(storage);
        return instance;
    }

    public List getLaunchedScripts() {
        return scripts;
    }

    private void parseScripts() throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        NodeList list = document.getFirstChild().getChildNodes();
        XPath xpath = XPathFactory.newInstance().newXPath();

        XPathExpression ex1;

        String pattern1;
        for (int i = 0; i < list.getLength(); i++) {

            pattern1 = "//scripts//script[" + i + "]//name";
            ex1 = xpath.compile(pattern1);
            String str = String.valueOf(ex1.evaluate(document, XPathConstants.STRING));
            if (!str.isEmpty()) {
                scripts.add(str);
            }
        }
    }

    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        return node.getNodeValue();
    }

    public void writeLaunchedScriptNamesInXml(String name) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        Element documentElement = document.getDocumentElement();
        //Create a Node element
        Element node = document.createElement("script");
        Element nameNode = document.createElement("name");
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
        Result result = new StreamResult(document.getDocumentURI());
        tFormer.transform(source, result);
    }

}
