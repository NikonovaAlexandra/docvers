package beans;

import java.awt.dnd.DropTargetEvent;
import java.io.Serializable;
import java.sql.Date;


/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 17.02.13
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */
public class VersionBean implements Serializable {
    private long id;
    private AuthorBean author;
    private DocumentBean document;
    private String description;
    private Date date;
    private String path;

    public VersionBean() {

    }

    public VersionBean(long id, AuthorBean author, DocumentBean document,
                       String description, Date date, String path) {
        this.id = id;
        this.author = author;
        this.document = document;
        this.description = description;
        this.date = date;
        this.path = path;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AuthorBean getAuthor() {

        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

    public DocumentBean getDocument() {

        return document;
    }

    public void setDocument(DocumentBean document) {
        this.document = document;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
