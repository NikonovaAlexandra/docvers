package servlets.documentServlet;

import exception.BusinessException;
import exception.NoSuchObjectInDB;
import exception.SystemException;
import servlets.ParentServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 27.02.13
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */
public class DeleteDocumentServlet extends ParentServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long docNameCode = Long.parseLong(request.getParameter("document to delete"));
            String login = requestParser.getAuthorBean(request).getLogin();
            service.deleteDocument(login, docNameCode);
            serverService.deleteUserDocumentFolder(filePath, login, docNameCode);
            showMessage(response);
        } catch (SystemException e) {
            throw new ServletException(e);
        } catch (BusinessException e) {
            if (e.getClass() == NoSuchObjectInDB.class) {
                request.setAttribute("docmessage", "message.documentBeenRemoved");
                RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/AllDocuments");
                reqDispatcher.forward(request, response);
            } else {
                throw new ServletException(e);
            }
        }
    }

    private void showMessage(HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/GetAllDocuments");

    }
}
