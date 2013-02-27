package servlets.documentServlet;

import beans.DocumentBean;
import exception.BusinessException;
import exception.ObjectAlreadyExistsException;
import exception.SystemException;
import service.DBOperations;
import service.RequestParser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 14.02.13
 * Time: 11:20
 * To change this template use File | Settings | File Templates.
 */
public class AddDocumentServlet extends HttpServlet {
    private RequestParser parser = RequestParser.getInstance();
    private DBOperations operations = DBOperations.getInstance();

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DocumentBean documentBean = null;
        try {
            documentBean = parser.getDocumentBean(request);
            operations.addDocument(documentBean);
            showSuccessfulAdditionPage(documentBean, request, response);
        } catch (BusinessException e) {
            if (e.getClass() == ObjectAlreadyExistsException.class) {
                showAlreadyExistsMessage(request, response);
            } else {
                throw new ServletException(e);
            }
        } catch (SystemException e) {
            throw new ServletException(e);
        }

    }

    private void showSuccessfulAdditionPage(DocumentBean doc, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = "Document \"" + doc.getName() + "\" was added successfully";
        showMessage(message, request, response);
    }

    private void showAlreadyExistsMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = "Document with the same name already exists";
        showMessage(message, request, response);
    }

    private void showMessage(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("addmessage", message);
        RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/AddDocument");
        reqDispatcher.forward(request, response);
    }


}
