package beans;

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
    private boolean released;
    private long versionName;
    private String versionType;

    public VersionBean() {

    }

    public VersionBean(long id, AuthorBean author, DocumentBean document,
                       String description, Date date, String path, String versionType,
                       boolean isReleased, long versionName) {
        this.id = id;
        this.author = author;
        this.document = document;
        this.description = description;
        this.date = date;
        this.path = path;
        this.versionType = versionType;
        this.released = isReleased;
        this.versionName = versionName;

    }
    public VersionBean(AuthorBean author, DocumentBean document,
                       String description, Date date, String path, String versionType) {
        this.author = author;
        this.document = document;
        this.description = description;
        this.date = date;
        this.path = path;
        this.versionType = versionType;

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

    public long getVersionName() {
        return versionName;
    }

    public void setVersionName(long versionName) {
        this.versionName = versionName;
    }

    public boolean getReleased() {

        return released;
    }

    public void setReleased(boolean released) {
        released = released;
    }

    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }
}
