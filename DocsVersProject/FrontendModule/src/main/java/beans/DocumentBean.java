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

    private AuthorBean author;
    private String name;
    private String description;
    private static long count = 0;

    public DocumentBean() {

    }

    public DocumentBean(AuthorBean author, String name, String description) {
        this.author = author;
        this.name = name;
        this.description = description;
    }

    public AuthorBean getAuthorBean() {
        return author;
    }

    public void setAuthorBean(AuthorBean author) {
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
