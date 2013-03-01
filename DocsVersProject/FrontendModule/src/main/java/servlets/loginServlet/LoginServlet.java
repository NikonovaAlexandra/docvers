package servlets.loginServlet;

import exception.BusinessException;
import exception.NoSuchObjectInDB;
import exception.SystemException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static service.Authentication.performLogin;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 24.02.13
 * Time: 23:37
 * To change this template use File | Settings | File Templates.
 */
public class LoginServlet extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        ServletContext context = request.getSession().getServletContext();

        try {
            performLogin(request, response, login, password, context);
        } catch (BusinessException e) {
            if (e.getClass() == new NoSuchObjectInDB("").getClass()) {
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


}
