package service;

import org.eclipse.mylyn.wikitext.confluence.core.ConfluenceLanguage;
import org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder;

import java.io.StringWriter;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 29.03.13
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
public class WikiTextInterpretator {

    public static String convertWikitextStringToString(String description) {
        StringWriter writer = new StringWriter();
        HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);

        // avoid the <html> and <body> tags
        builder.setEmitAsDocument(false);
        MarkupParser parser = new MarkupParser(new ConfluenceLanguage());
        parser.setBuilder(builder);
        parser.parse(description);
        return writer.toString();

    }
}
