package service;

import beans.AuthorBean;
import entities.Author;
import exception.BusinessException;
import exception.NoSuchObjectInDB;
import exception.SystemException;

import javax.servlet.RequestDispatcher;
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
    public static AuthorBean authenticate(Author author, String login, String password) throws BusinessException, SystemException {
        if (author.getLogin().equals(login) && author.getPassword().equals(password)) {
            return new AuthorBean(login, password);
        } else return null;
    }

    public static void performLogin(HttpServletRequest request, HttpServletResponse response, String login,
                              String password, ServletContext context) throws ServletException, IOException, BusinessException, SystemException {
        DBOperations operations = DBOperations.getInstance();
        if ((login != null) & (password != null)) {
            Author author = operations.getAuthorByLogin(login);
            if (author != null) {
                AuthorBean tok = authenticate(author, login, password);
                if (tok != null) {
                    request.getSession().setAttribute("user", tok);
                    RequestDispatcher reqDispatcher = context.getRequestDispatcher("/Menu");
                    reqDispatcher.forward(request, response);
                } else throw new NoSuchObjectInDB("Incorrect login or password!");
            } else {
                String url = context.getInitParameter("login_page");
                if (url != null && !"".equals(url)) {
                    response.sendRedirect(url);
                }
            }
        } else {
            String url = context.getInitParameter("login_page");
            if (url != null && !"".equals(url)) {
                response.sendRedirect(url);
            }
        }
    }
}
