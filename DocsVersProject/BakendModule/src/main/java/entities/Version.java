package entities;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 07.02.13
 * Time: 10:26
 * To change this template use File | Settings | File Templates.
 */
public class Version {
    private long id;
    private String name;
    private Document document;
    private Author author;
    private Date date;
    private String versionDescription;
    private String documentPath;

    public Version() {
    }

    public Version(long id, String name, Document document, Author author, Date date,
                   String versionDescription, String documentPath) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.author = author;
        this.date = date;
        this.versionDescription = versionDescription;
        this.documentPath = documentPath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Document getDocument() {

        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getVersionDescription() {
        return versionDescription;
    }

    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
}
