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
        return new DocumentBean(doc.getId(), convertAuthorToAuthorBean(auth), doc.getDocumentName(), doc.getDescription(), doc.getCodeDocumentName());
    }

    public static DocumentBean convertDocumentHToDocumentBean(Document doc) {
        return new DocumentBean(doc.getId(), convertAuthorToAuthorBean(doc.getAuthorId()), doc.getDocumentName(), doc.getDescription(), doc.getCodeDocumentName());
    }

    public static Document convertDocumentBeanToDocument(DocumentBean doc) {
        Document document = new Document(doc.getAuthor().getId(), doc.getName(), doc.getDescription(), doc.getCodeDocumentName());
        document.setId(doc.getId());
        return document;
    }

    public static Document convertDocumentBeanToDocumentH(DocumentBean doc) {
        Document document = new Document();
        document.setId(doc.getId());
        document.setAuthorId(convertAuthorBeanToAuthor(doc.getAuthor()));
        document.setDocumentName(doc.getName());
        document.setDescription(doc.getDescription());
        document.setCodeDocumentName(doc.getCodeDocumentName());
        return document;
    }

    public static AuthorBean convertAuthorToAuthorBean(Author author) {
        return new AuthorBean(author.getId(), author.getLogin(), author.getPassword());
    }

    public static Author convertAuthorBeanToAuthor(AuthorBean author) {
        return new Author(author.getId(), author.getLogin(), author.getPassword());
    }

    public static VersionBean convertVersionToVersionBean(Version ver, Document doc, Author authorDoc, Author authorVers) {
        return new VersionBean(ver.getId(), convertAuthorToAuthorBean(authorVers),
                convertDocumentToDocumentBean(doc, authorDoc),
                ver.getVersionDescription(), ver.getDate(), ver.getDocumentPath(), ver.getVersionType(), ver.isReleased(), ver.getVersionName());
    }

    public static Version convertVersionBeanToVersion(VersionBean vers) {
        return new Version(vers.getId(), vers.getDocument().getId(), vers.getAuthor().getId(),
                vers.getDate(), vers.getDescription(), vers.getPath(), vers.getVersionType(), vers.getVersionName());
    }

    public static Version convertVersionBeanToVersionH(VersionBean vers) {
        Version version = new Version();
        version.setId(vers.getId());
        version.setAuthorId(convertAuthorBeanToAuthor(vers.getAuthor()));
        version.setDocumentId(convertDocumentBeanToDocumentH(vers.getDocument()));
        version.setDocumentPath(vers.getPath());
        version.setDate(vers.getDate());
        version.setReleased(vers.getReleased());
        version.setVersionName(vers.getVersionName());
        version.setVersionType(vers.getVersionType());
        version.setVersionDescription(vers.getDescription());
        return version;
    }

    public static VersionBean convertVersionHToVersionBean(Version ver) {
        return new VersionBean(ver.getId(), convertAuthorToAuthorBean(ver.getAuthorId()),
                convertDocumentHToDocumentBean(ver.getDocumentId()),
                ver.getVersionDescription(), ver.getDate(), ver.getDocumentPath(), ver.getVersionType(), ver.isReleased(), ver.getVersionName());
    }
}
