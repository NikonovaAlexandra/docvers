package servlets.loginServlet;

import beans.AuthorBean;
import beans.DocumentBean;
import entities.Author;
import exception.BusinessException;
import exception.NoSuchObjectInDB;
import exception.ObjectAlreadyExistsException;
import exception.SystemException;
import util.Authentication;
import util.DBOperations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static util.Authentication.*;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 24.02.13
 * Time: 23:37
 * To change this template use File | Settings | File Templates.
 */
public class LoginServlet extends HttpServlet{
    private DBOperations operations = DBOperations.getInstance();
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        ServletContext context = request.getSession().getServletContext();
        try {
            performLogin(request,response,login, password, context);
        } catch (BusinessException e) {
            if(e.getClass() == new NoSuchObjectInDB("").getClass()) {
                request.getSession().setAttribute("logmessage", "Incorrect login or password!");
                String url = context.getInitParameter("login_page");
                if (url != null && !"".equals(url)) {
                    response.sendRedirect(url);
                }
            }
        } catch (SystemException e) {
            throw new ServletException(e);
        }

    }
    private void performLogin(HttpServletRequest request, HttpServletResponse response, String login,
                              String password, ServletContext context) throws ServletException, IOException, BusinessException, SystemException {
        if((login!=null) & (password!=null)) {
            Author author = operations.getAuhorByLogin(login);
            if(author!=null) {
                AuthorBean tok = authenticate(author, login, password);
                if (tok != null) {
                    request.getSession().setAttribute("user", tok);
                    RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/Menu");
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
