package entities;

import java.sql.Clob;
import java.sql.Date;


/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 07.02.13
 * Time: 10:26
 * To change this template use File | Settings | File Templates.
 */
public class Version {
    private long id;
    private long documentID;
    private long authorID;
    private Date date;
    private String versionDescription;
    private String documentPath;

    public Version() {
    }

    public Version(long id, long documentID, long authorID, Date date,
                   String versionDescription, String documentPath) {
        this.id = id;
        this.documentID = documentID;
        this.authorID = authorID;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getDocumentID() {

        return documentID;
    }

    public void setDocumentID(long documentID) {
        this.documentID = documentID;
    }

    public long getAuthorID() {
        return authorID;
    }

    public void setAuthorID(long authorID) {
        this.authorID = authorID;
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
