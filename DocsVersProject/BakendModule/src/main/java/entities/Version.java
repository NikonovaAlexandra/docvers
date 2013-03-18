package entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 14.03.13
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "VERSION", schema = "PUBLIC", catalog = "DOCS")
@Entity
public class Version {
    private long id;

    @javax.persistence.Column(name = "ID")
    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private Timestamp date;

    @javax.persistence.Column(name = "DATE")
    @Basic
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    private String versionDescription;

    @javax.persistence.Column(name = "VERSION_DESCRIPTION")
    @Basic
    public String getVersionDescription() {
        return versionDescription;
    }

    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
    }

    private String documentPath;

    @javax.persistence.Column(name = "DOCUMENT_PATH")
    @Basic
    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    private boolean isReleased;

    @javax.persistence.Column(name = "IS_RELEASED")
    @Basic
    public boolean isReleased() {
        return isReleased;
    }

    public void setReleased(boolean released) {
        isReleased = released;
    }

    private String versionType;

    @javax.persistence.Column(name = "VERSION_TYPE")
    @Basic
    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }

    private long versionName;

    @javax.persistence.Column(name = "VERSION_NAME")
    @Basic
    public long getVersionName() {
        return versionName;
    }

    public void setVersionName(long versionName) {
        this.versionName = versionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Version that = (Version) o;

        if (id != that.id) return false;
        if (isReleased != that.isReleased) return false;
        if (versionName != that.versionName) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (documentPath != null ? !documentPath.equals(that.documentPath) : that.documentPath != null) return false;
        if (versionDescription != null ? !versionDescription.equals(that.versionDescription) : that.versionDescription != null)
            return false;
        if (versionType != null ? !versionType.equals(that.versionType) : that.versionType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (versionDescription != null ? versionDescription.hashCode() : 0);
        result = 31 * result + (documentPath != null ? documentPath.hashCode() : 0);
        result = 31 * result + (isReleased ? 1 : 0);
        result = 31 * result + (versionType != null ? versionType.hashCode() : 0);
        result = 31 * result + (int) (versionName ^ (versionName >>> 32));
        return result;
    }

    private Author authorId;

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID", nullable = false)
    public Author getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Author authorId) {
        this.authorId = authorId;
    }

    private Document documentId;

    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "ID", nullable = false)
    public Document getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Document documentId) {
        this.documentId = documentId;
    }

    @Transient
    private long documentID;
    public long getDocumentID() {

        return documentID;
    }

    public void setDocumentID(long documentID) {
        this.documentID = documentID;
    }

    @Transient
    private long authorID;
    public long getAuthorID() {
        return authorID;
    }

    public void setAuthorID(long authorID) {
        this.authorID = authorID;
    }

    public Version() {
    }

    public Version(long id, long documentID, long authorID, Timestamp date,
                   String versionDescription, String documentPath, String versionType, long versionName) {
        this.id = id;
        this.documentID = documentID;
        this.authorID = authorID;
        this.date = date;
        this.versionDescription = versionDescription;
        this.documentPath = documentPath;
        this.versionType = versionType;
        this.versionName = versionName;

    }



}
