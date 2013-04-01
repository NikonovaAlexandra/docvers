package servlets.documentServlet;

import beans.DocumentBean;
import exception.*;
import exception.IllegalArgumentException;
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
    private static final String messageName = "addmessage";
    private static final String url = "/AddDocument";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        DocumentBean documentBean = null;
        try {
            documentBean = getRequestParser().getDocumentBean(request);
            getService().addDocument(documentBean);
            getFileFolderService().createUserDocumentFolder(getFilePath(),
                    documentBean.getAuthor().getLogin(), documentBean.getCodeDocumentName());
            showMessage(request, response, "message.successfullyAdded", messageName, url);
        } catch (BusinessException e) {
            if (e.getClass() == ObjectAlreadyExistsException.class) {
                showMessage(request, response, "message.documentAlreadyExists", messageName, url);
            } else if (e.getClass() == IllegalArgumentException.class) {
                showMessage(request, response, "message.tooLongDocumentName", messageName, url);
            } else {
                throw new ServletException(e);
            }
        } catch (MyException e) {
            throw new ServletException(e);
        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       response.sendRedirect(url + "?language=" + request.getParameter("language"));
    }

}
