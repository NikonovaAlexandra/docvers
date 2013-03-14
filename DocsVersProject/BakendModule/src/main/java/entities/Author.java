package entities;


import javax.persistence.*;
/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 07.02.13
 * Time: 10:31
 * To change this template use File | Settings | File Templates.
 */

//@Table(name = "AUTHOR", uniqueConstraints = @UniqueConstraint(name = "LOGIN_UNIQUE", columnNames = "LOGIN"), catalog = "docs")
@Entity

public class Author {

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

    @Column(name = "LOGIN", length = 255, nullable = false)

    private String login;
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "PASSWORD", length = 255, nullable = false)
    private String password;
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Author() {
    }

    public Author(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

}
