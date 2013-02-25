package servlets.loginServlet;

import beans.AuthorBean;
import entities.Author;
import exception.BusinessException;
import exception.NoSuchObjectInDB;
import exception.SystemException;
import util.DBOperations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static util.Authentication.authenticate;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 25.02.13
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */
public class LogoutServlet extends HttpServlet {

        private DBOperations operations = DBOperations.getInstance();
        public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            HttpSession session = request.getSession(false);

            if(session != null){
                session.invalidate();
            }
            RequestDispatcher rd = request.getRequestDispatcher("Login");
            rd.forward(request, response);

        }

}
