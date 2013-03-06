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

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("version"));
            setDocumentName((Long) request.getSession().getAttribute("documentToView"));
            String login = requestParser.getAuthorBean(request).getLogin();
            String type = service.getVersionType(id, getDocumentName(), login);
            serverService.deleteUserDocumentVersion(filePath, login, getDocumentName(), id, type);
            service.deleteVersion(id, getDocumentName(), login);
            showMessage(request, response);
        } catch (SystemException e) {
            throw new ServletException(e);
        } catch (BusinessException e) {
            if (e.getClass() == NoSuchObjectInDB.class) {
                request.setAttribute("versmessage", "message.versionBeenRemoved");
                RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/AllVersions");
                reqDispatcher.forward(request, response);
            } else {
                throw new ServletException(e);
            }
        }
    }

    private void showMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//            RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/Versions?document=" + document);
//            reqDispatcher.forward(request, response);
        response.sendRedirect("/Versions?document=" + getDocumentName());
    }
}
