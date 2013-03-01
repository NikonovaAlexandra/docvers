package beans;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 17.02.13
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */
public class DocumentBean implements Serializable {
    private long id;
    private AuthorBean author;
    private String name;
    private String description;

    public DocumentBean() {

    }

    public DocumentBean(AuthorBean author, String name, String description) {
        this.author = author;
        this.name = name;
        this.description = description;
    }

    public DocumentBean(long id, AuthorBean author, String name, String description) {
        this.id = id;
        this.author = author;
        this.name = name;
        this.description = description;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
