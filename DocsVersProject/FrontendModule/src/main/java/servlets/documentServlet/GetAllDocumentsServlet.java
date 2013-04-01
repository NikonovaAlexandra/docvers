package servlets.documentServlet;

import beans.DocumentBean;
import exception.BusinessException;
import exception.MyException;
import exception.NoSuchObjectInDB;
import exception.SystemException;
import service.WikiTextInterpretator;
import servlets.ParentServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 20.02.13
 * Time: 11:30
 * To change this template use File | Settings | File Templates.
 */
public class GetAllDocumentsServlet extends ParentServlet {

    private final String messageName = "docmessage";
    private final String attrName = "documentList";
    private final String url = "/AllDocuments";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            List<DocumentBean> docs = getService().getDocumentsByAuthor(
                    getRequestParser().getAuthorBean(request).getLogin());
            for(DocumentBean doc: docs) {
                String description = WikiTextInterpretator.convertWikitextStringToString(doc.getDescription());
                doc.setDescription(description);
            }
            showMessage(request, response, docs, attrName, url);
        } catch (SystemException e) {
            throw new ServletException(e);
        } catch (BusinessException e) {
            if (e.getClass() == NoSuchObjectInDB.class) {
                showMessage(request, response, "message.hoveNotAnyDocument", messageName, url);
            } else {
                throw new ServletException(e);
            }
        }  catch (MyException e) {
            throw new ServletException(e);
        }
    }
}