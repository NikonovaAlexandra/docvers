package servlets.documentServlet;

import exception.BusinessException;
import exception.MyException;
import exception.NoSuchObjectInDB;
import exception.SystemException;
import servlets.ParentServlet;

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

    private static final String messageName = "docmessage";
    private static final String attrName = "documentList";
    private static final String url1 = "/AllDocuments";
    private static final String url2 = "/GetAllDocuments";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding(getEncoding());
        request.setCharacterEncoding(getEncoding());
        try {
            long docNameCode = Long.parseLong(request.getParameter("document to delete"));
            String login = getRequestParser().getAuthorBean(request).getLogin();
            getService().deleteDocument(login, docNameCode);
            getFileFolderService().deleteUserDocumentFolder(getFilePath(), login, docNameCode);
            response.sendRedirect(url2);
        } catch (SystemException e) {
            throw new ServletException(e);
        } catch (BusinessException e) {
            if (e.getClass() == NoSuchObjectInDB.class) {
                showMessage(request, response, "message.documentBeenRemoved", messageName, url1);
            } else {
                throw new ServletException(e);
            }
        }  catch (MyException e) {
            throw new ServletException(e);
        }
    }

}
