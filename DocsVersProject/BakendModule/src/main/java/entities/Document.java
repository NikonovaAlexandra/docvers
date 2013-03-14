package entities;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 07.02.13
 * Time: 10:30
 * To change this template use File | Settings | File Templates.
 */
//@Table(name = "DOCUMENT", catalog = "docs", uniqueConstraints = @UniqueConstraint(name = "UNIQUE_DOCUMENT_NAME", columnNames = "author_id, document_name"))
@Entity
public class Document {

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

    @Column(name = "AUTHOR_ID")
    //todo: fk
    private long authorID;
    public long getAuthorID() {
        return authorID;
    }

    public void setAuthorID(long authorID) {
        this.authorID = authorID;
    }

    @Column(name = "DOCUMENT_NAME", length = 20, nullable = false)
    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DOCUMENT_DECSRIPTION")
    @Lob
    private String description;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "code_Document_Name", nullable = false)
    private long codeDocumentName;
    public long getCodeDocumentName() {
        return codeDocumentName;
    }

    public void setCodeDocumentName(long codeDocumentName) {
        this.codeDocumentName = codeDocumentName;
    }

    public Document() {
    }

    public Document(long authorID, String name, String description, long codeDocumentName) {
        this.authorID = authorID;
        this.name = name;
        this.description = description;
        this.codeDocumentName = codeDocumentName;
    }










}
