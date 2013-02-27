package servlets.versionServlet;

import beans.VersionBean;
import exception.BusinessException;
import exception.NoSuchObjectInDB;
import exception.SystemException;
import service.DBOperations;
import service.RequestParser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* Created with IntelliJ IDEA.
* User: alni
* Date: 26.02.13
* Time: 12:00
* To change this template use File | Settings | File Templates.
*/
public class GetVersionsServlet extends HttpServlet {

    private DBOperations operations = DBOperations.getInstance();

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String docName = request.getParameter("docVers");
            List<VersionBean> vers = operations.getVersionsOfDocument(RequestParser.getInstance().getAuthorBean(request).getLogin(), docName);
            showVersions(vers, request, response);
        } catch (SystemException e) {
            System.out.println(e.getCause() + e.getMessage());
            throw new ServletException(e);
        } catch (BusinessException e) {
            if (e.getClass() == NoSuchObjectInDB.class) {
                request.setAttribute("noanyversmessage", "There are no such object: " + e.toString());
                RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/AllVersions");
                reqDispatcher.forward(request, response);
            } else {
                throw new ServletException(e);
            }
        }
    }

    private void showVersions(List<VersionBean> vers, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("versionList", vers);
        RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/AllVersions");
        reqDispatcher.forward(request, response);

    }
}
