package entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 14.03.13
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "DOCUMENT", schema = "PUBLIC", catalog = "DOCS")
@Entity
public class Document {
    private long id;

    @javax.persistence.Column(name = "ID")
    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String documentName;

    @javax.persistence.Column(name = "DOCUMENT_NAME")
    @Basic
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    private String description;

    @javax.persistence.Column(name = "DESCRIPTION")
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private long codeDocumentName;

    @javax.persistence.Column(name = "CODE_DOCUMENT_NAME")
    @Basic
    public long getCodeDocumentName() {
        return codeDocumentName;
    }

    public void setCodeDocumentName(long codeDocumentName) {
        this.codeDocumentName = codeDocumentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document that = (Document) o;

        if (codeDocumentName != that.codeDocumentName) return false;
        if (id != that.id) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (documentName != null ? !documentName.equals(that.documentName) : that.documentName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (documentName != null ? documentName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) (codeDocumentName ^ (codeDocumentName >>> 32));
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

    @Transient
    private long authorID;

    @Transient
    public long getAuthorID() {
        return authorID;
    }

    @Transient
    public void setAuthorID(long authorID) {
        this.authorID = authorID;
    }

    public Document() {
    }

    public Document(long authorID, String name, String description, long codeDocumentName) {
        this.authorID = authorID;
        this.documentName = name;
        this.description = description;
        this.codeDocumentName = codeDocumentName;
    }
}
