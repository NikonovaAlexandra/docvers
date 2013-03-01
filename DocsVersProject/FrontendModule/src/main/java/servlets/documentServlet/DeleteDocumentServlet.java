package servlets.documentServlet;

import beans.DocumentBean;
import beans.VersionBean;
import exception.BusinessException;
import exception.NoSuchObjectInDB;
import exception.ObjectAlreadyExistsException;
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
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 27.02.13
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */
public class DeleteDocumentServlet extends HttpServlet {
    private DBOperations service= DBOperations.getInstance();
    private ServerOperations serverService = ServerOperations.getInstance();
    private String filePath;

    public void init( ){
        // Get the file location where it would be stored.
        filePath =
                getServletContext().getInitParameter("file-upload");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String docName = request.getParameter("document to delete");
            String login = RequestParser.getInstance().getAuthorBean(request).getLogin();
            service.deleteDocument(login, docName);
            serverService.deleteUserDocumentFolder(filePath, login, docName );
            showMessage(response);
        } catch (SystemException e) {
            System.out.println(e.getCause() + e.getMessage());
            throw new ServletException(e);
        } catch (BusinessException e) {
            if (e.getClass() == NoSuchObjectInDB.class) {
                request.setAttribute("docmessage", "This document has already been removed.");
                RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/AllDocuments");
                reqDispatcher.forward(request, response);
            } else {
                throw new ServletException(e);
            }
        }
    }

    private void showMessage( HttpServletResponse response) throws ServletException, IOException {
       response.sendRedirect("/GetAllDocuments");

    }
}
