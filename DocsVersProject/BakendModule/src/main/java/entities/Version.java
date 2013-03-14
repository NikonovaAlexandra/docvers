package entities;

import javax.persistence.*;
import java.sql.Date;


/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 07.02.13
 * Time: 10:26
 * To change this template use File | Settings | File Templates.
 */

//@Table(name = "VERSION", uniqueConstraints = @UniqueConstraint(name = "LOGIN_UNIQUE", columnNames = "LOGIN"), catalog = "docs")
@Entity
public class Version {

    @Column(name = "ID")
    @GeneratedValue
    @Id
    private long id;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    //todo: fk
    @Column(name = "DOCUMENT_ID", nullable = false)
    private long documentID;
    public long getDocumentID() {

        return documentID;
    }

    public void setDocumentID(long documentID) {
        this.documentID = documentID;
    }

    //todo: fk
    @Column(name = "AUTHOR_ID", nullable = false)
    private long authorID;
    public long getAuthorID() {
        return authorID;
    }

    public void setAuthorID(long authorID) {
        this.authorID = authorID;
    }

    @Column(name = "DATE", nullable = false)
    private Date date;
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "VERSION_DESCRIPTION")
    private String versionDescription;
    public String getVersionDescription() {
        return versionDescription;
    }

    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
    }

    @Column(name = "DOCUMENT_PATH", nullable = false)
    private String documentPath;
    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    @Column(name = "IS_RELEASED", nullable = false)
    private boolean isReleased;
    public boolean isReleased() {

        return isReleased;
    }

    public void setReleased(boolean released) {
        isReleased = released;
    }

    @Column(name = "VERSION_NAME", length = 255, nullable = false)
    private long versionName;
    public long getVersionName() {
        return versionName;
    }

    public void setVersionName(long versionName) {
        this.versionName = versionName;
    }

    @Column(name = "VERSION_TYPE", length = 5, nullable = false)
    private String versionType;
    public String getVersionType() {
        return versionType;
    }

    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }

    public Version() {
    }

    public Version(long id, long documentID, long authorID, Date date,
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
