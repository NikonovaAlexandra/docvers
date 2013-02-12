package entities;

import java.sql.Clob;
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
    private long documentID;
    private long authorID;
    private Date date;
    private Clob versionDescription;
    private String documentPath;

    public Version() {
    }

    public Version(long id, String name, long documentID, long authorID, Date date,
                   Clob versionDescription, String documentPath) {
        this.id = id;
        this.name = name;
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

    public Clob getVersionDescription() {
        return versionDescription;
    }

    public void setVersionDescription(Clob versionDescription) {
        this.versionDescription = versionDescription;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
}
