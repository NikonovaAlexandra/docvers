package servlets.documentServlet;

import beans.DocumentBean;
import exception.BusinessException;
import exception.ObjectAlreadyExistsException;
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
 * Date: 14.02.13
 * Time: 11:20
 * To change this template use File | Settings | File Templates.
 */
public class AddDocumentServlet extends ParentServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DocumentBean documentBean = null;
        try {
            documentBean = requestParser.getDocumentBean(request);
            service.addDocument(documentBean);
            serverService.createUserDocumentFolder(filePath, documentBean.getAuthor().getLogin(), documentBean.getCodeDocumentName());
            showSuccessfulAdditionPage(request, response);
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

    private void showSuccessfulAdditionPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = "message.successfullyAdded";
        showMessage(message, request, response);
    }

    private void showAlreadyExistsMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = "message.documentAlreadyExists";
        showMessage(message, request, response);
    }

    private void showMessage(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("addmessage", message);
        RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/AddDocument");
        reqDispatcher.forward(request, response);
    }


}
