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
    private final String messageName = "addmessage";
    private final String url = "/AddDocument";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setCharacterEncoding(getEncoding());
        request.setCharacterEncoding(getEncoding());

        DocumentBean documentBean = null;
        try {
            documentBean = getRequestParser().getDocumentBean(request);
            getService().addDocument(documentBean);
            getServerService().createUserDocumentFolder(getFilePath(),
                    documentBean.getAuthor().getLogin(), documentBean.getCodeDocumentName());
            showMessage(request, response, "message.successfullyAdded", messageName, url);
        } catch (BusinessException e) {
            if (e.getClass() == ObjectAlreadyExistsException.class) {
                showMessage(request, response, "message.documentAlreadyExists", messageName, url);
            } else {
                throw new ServletException(e);
            }
        } catch (SystemException e) {
            throw new ServletException(e);
        }

    }

}
