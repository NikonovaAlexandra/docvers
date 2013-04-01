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

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 31.03.13
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 */
public class ViewDocumentServlet extends ParentServlet {

    private static final String messageName = "docmessage";
    private static final String url1 = "/AllDocuments";
    private static final String url2 = "/ViewDocument";
    private static final String url3 = "/EditDocument";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            DocumentBean doc = parseDocumentBean(request);
            if (request.getRequestURI().equals("/ViewDocumentServlet")) {
                String decsr = doc.getDescription();
                doc.setDescription(WikiTextInterpretator.convertWikitextStringToString(decsr));
                showMessage(request, response, doc, "documentItem", url2);
            } else {
                showMessage(request, response, doc, "documentItem", url3);
            }
        } catch (SystemException e) {
            throw new ServletException(e);
        } catch (BusinessException e) {
            if (e.getClass() == NoSuchObjectInDB.class) {
                showMessage(request, response, "message.documentBeenRemoved", messageName, url1);
            } else {
                throw new ServletException(e);
            }
        } catch (MyException e) {
            throw new ServletException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

}
