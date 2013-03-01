package servlets.versionServlet;

import beans.VersionBean;
import exception.BusinessException;
import exception.NoSuchObjectInDB;
import exception.SystemException;
import service.DBOperations;
import service.RequestParser;
import service.ServerOperations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* Created with IntelliJ IDEA.
* User: alni
* Date: 28.02.13
* Time: 12:40
* To change this template use File | Settings | File Templates.
*/
public class DeleteVersionServlet extends HttpServlet {
    private DBOperations service = DBOperations.getInstance();
    private String documentName;
    private ServerOperations serverService = ServerOperations.getInstance();
    private String filePath;

    public void init( ){
        // Get the file location where it would be stored.
        filePath =
                getServletContext().getInitParameter("file-upload");
    }
        public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                long id  = Long.parseLong(request.getParameter("version to delete"));
                documentName = request.getParameter("document");
                String login = RequestParser.getInstance().getAuthorBean(request).getLogin();
                service.deleteVersion(id);
                serverService.deleteUserDocumentVersion(filePath, login, documentName, id);
                showMessage(request, response);
            } catch (SystemException e) {
                System.out.println(e.getCause() + e.getMessage());
                throw new ServletException(e);
            } catch (BusinessException e) {
                if (e.getClass() == NoSuchObjectInDB.class) {
                    request.setAttribute("versmessage", "This version has already been removed.");
                    RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/AllVersions");
                    reqDispatcher.forward(request, response);
                } else {
                    throw new ServletException(e);
                }
            }
        }

        private void showMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//            RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/Versions?document=" + document);
//            reqDispatcher.forward(request, response);
            response.sendRedirect("/Versions?document=" + documentName);
        }
}
