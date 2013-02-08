package entities;

import java.sql.Clob;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 07.02.13
 * Time: 10:30
 * To change this template use File | Settings | File Templates.
 */
public class Document {

    private long id;
    private Author author;
    private String name;
    private Clob description;

    public Document() {
    }

    public Document(long id, Author author, String name, Clob description) {
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Clob getDescription() {
        return description;
    }

    public void setDescription(Clob description) {
        this.description = description;
    }
}