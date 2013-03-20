package servlets.loginServlet;

import exception.BusinessException;
import exception.NoSuchObjectInDB;
import exception.SystemException;
import servlets.ParentServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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
public class LoginServlet extends ParentServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //todo to filter
        response.setCharacterEncoding(getEncoding());
        request.setCharacterEncoding(getEncoding());

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        ServletContext context = request.getSession().getServletContext();
        //todo: loginmessage after incorrect login
        try {
            performLogin(request, response, login, password, context);
        } catch (BusinessException e) {
            if (e.getClass() == new NoSuchObjectInDB("No User with such login and password!").getClass()) {
                String url = context.getInitParameter("login_page");
                request.getSession().setAttribute("logmessage", "message.incorrectLoginOrPassword");
                if (url != null && !"".equals(url)) {
                    response.sendRedirect(url);
                }
            }
        } catch (SystemException e) {
            throw new ServletException(e);
        }

    }


}
