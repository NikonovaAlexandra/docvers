package beans;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 17.02.13
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */
public class AuthorBean implements Serializable {
    private String login;
    private String password;

    public AuthorBean() {
    }

    public AuthorBean(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
