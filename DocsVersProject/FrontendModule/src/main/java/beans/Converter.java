package beans;

import entities.Author;
import entities.Document;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 20.02.13
 * Time: 11:34
 * To change this template use File | Settings | File Templates.
 */
public class Converter {
    public static DocumentBean convertDocumentToDocumentBean(Document doc, Author auth) {
        return new DocumentBean(convertAuthorToAuthortBean(auth), doc.getName(), doc.getDescription());
    }

    public static AuthorBean convertAuthorToAuthortBean(Author author) {
        AuthorBean authorBean = new AuthorBean(author.getLogin(), author.getPassword());
        return authorBean;
    }
}
