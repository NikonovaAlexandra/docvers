package servlets.versionServlet;

import beans.VersionBean;
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
 * Date: 28.02.13
 * Time: 12:40
 * To change this template use File | Settings | File Templates.
 */
public class DeleteVersionServlet extends ParentServlet {
    private final String url1 = "/Versions?document=";
    private final String url2 = "/AllVersions";
    private final String messageName = "versmessage";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("version"));
            setDocumentName((Long) request.getSession().getAttribute("documentToView"));
            String login = getRequestParser().getAuthorBean(request).getLogin();
            String type = getService().getVersionType(id, getDocumentName(), login);
            getServerService().deleteUserDocumentVersion(getFilePath(), login, getDocumentName(), id, type);
            getService().deleteVersion(id, getDocumentName(), login);
            response.sendRedirect(url1 + getDocumentName());
        } catch (SystemException e) {
            throw new ServletException(e);
        } catch (BusinessException e) {
            if (e.getClass() == NoSuchObjectInDB.class) {
                showMessage(request, response, "message.versionBeenRemoved", messageName, url2);
            } else {
                throw new ServletException(e);
            }
        }
    }
}
