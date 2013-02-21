package util;

import beans.AuthorBean;
import beans.DocumentBean;
import exception.NullConnectionException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 17.02.13
 * Time: 16:39
 * To change this template use File | Settings | File Templates.
 */
public class RequestParser {
    private static RequestParser instance;

    public static synchronized RequestParser getInstance(){
        if (instance == null){
            instance = new RequestParser();
        }
        return instance;
    }

    public AuthorBean getAuthorBean() {
        AuthorBean a = new AuthorBean();
        a.setLogin("author1");
        a.setPassword("pass1");
        return a;
    }

    public DocumentBean getDocumentBean(HttpServletRequest request)throws SQLException, NullConnectionException {
        AuthorBean author = getAuthorBean();
        String name = request.getParameter("docname");
        String description = request.getParameter("docdescription");
        return new DocumentBean(author, name, description);
    }
}
