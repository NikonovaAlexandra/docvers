package service;

import beans.AuthorBean;
import dao.DAOType;
import exception.BusinessException;
import exception.MyException;
import exception.NoSuchObjectInDB;
import exception.SystemException;
import service.dbOperations.DBOperations;
import service.dbOperations.DBOperationsFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 25.02.13
 * Time: 0:06
 * To change this template use File | Settings | File Templates.
 */
public class Authentication {
    public static AuthorBean authenticate(AuthorBean author, String login, String password) throws BusinessException, SystemException {
        if (author.getLogin().equals(login) && author.getPassword().equals(password)) {
            return new AuthorBean(author.getId(), login, password);
        } else return null;
    }

    public static void performLogin(HttpServletRequest request, HttpServletResponse response, String login,
                                    String password, ServletContext context) throws ServletException, IOException, MyException {

        DBOperations operations = DBOperationsFactory.getDBService((DAOType)context.getAttribute("type"));
        if ((login != null) & (password != null)) {
            AuthorBean author = operations.getAuthorByLogin(login);
            if (author != null) {
                AuthorBean tok = authenticate(author, login, password);
                if (tok != null) {
                    request.getSession().removeAttribute("logmessage");
                    request.getSession().setAttribute("user", tok);
                    response.sendRedirect("/GetAllDocuments");
                } else throw new NoSuchObjectInDB("Incorrect login or password!");
            } else {
                throw new NoSuchObjectInDB("Incorrect login or password!");
            }
        } else {
            String url = context.getInitParameter("login_page");
            if (url != null && !"".equals(url)) {
                response.sendRedirect(url);
            }
        }
    }
}
