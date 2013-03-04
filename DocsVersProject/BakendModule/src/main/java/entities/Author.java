package entities;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 07.02.13
 * Time: 10:31
 * To change this template use File | Settings | File Templates.
 */
public class Author {

    private long id;
    private String login;
    private String password;
    private static long count = 0;

    public Author() {
        id = count;

// todo : by restart count = 0, but entities are in db already...
        ++count;
    }

    public Author(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;

    }
}
