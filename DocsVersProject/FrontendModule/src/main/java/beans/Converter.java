package beans;

import entities.Author;
import entities.Document;
import entities.Version;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 20.02.13
 * Time: 11:34
 * To change this template use File | Settings | File Templates.
 */
public class Converter {
    public static DocumentBean convertDocumentToDocumentBean(Document doc, Author auth) {
        return new DocumentBean(doc.getId(), convertAuthorToAuthorBean(auth), doc.getName(), doc.getDescription());
    }

    public static Document convertDocumentBeanToDocument(DocumentBean doc) {
        Document document = new Document(doc.getAuthor().getId(), doc.getName(), doc.getDescription());
        document.setId(doc.getId());
        return document;
    }

    public static AuthorBean convertAuthorToAuthorBean(Author author) {
        return new AuthorBean(author.getId(), author.getLogin(), author.getPassword());
    }

    public static VersionBean convertVersionToVersionBean(Version ver, Document doc, Author authorDoc, Author authorVers) {
        return new VersionBean(ver.getId(), convertAuthorToAuthorBean(authorVers),
                convertDocumentToDocumentBean(doc, authorDoc),
                ver.getVersionDescription(), ver.getDate(), ver.getDocumentPath() );
    }

    public static Version convertVersionBeanToVersion(VersionBean vers) {
       return new Version(vers.getId(), vers.getDocument().getId(), vers.getAuthor().getId(),
                vers.getDate(), vers.getDescription(), vers.getPath());
    }
}
