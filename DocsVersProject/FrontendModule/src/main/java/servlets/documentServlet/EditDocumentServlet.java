package servlets.documentServlet;

import beans.DocumentBean;
import beans.VersionBean;
import exception.MyException;
import servlets.ParentServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 31.03.13
 * Time: 17:38
 * To change this template use File | Settings | File Templates.
 */
public class EditDocumentServlet extends ParentServlet {
    private static final String url = "/GetEditDocument";
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        DocumentBean documentBean = null;
        try {
            documentBean = parseDocumentBean(request);
            String description = request.getParameter("description").trim();
            if ( !documentBean.getDescription().equals(description)) {
                documentBean.setDescription(description);
                getService().editDocumentDescription(documentBean);
                showMessage(request, response, "message.save", "editmessage", url);
            } else {
                showMessage(request, response, "message.nothigToSave", "editmessage", url);
            }

        } catch (MyException e) {
            throw new ServletException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect(url + "?language=" + request.getParameter("language"));
    }
}