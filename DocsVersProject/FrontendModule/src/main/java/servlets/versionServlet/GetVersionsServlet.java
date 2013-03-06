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
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 26.02.13
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
public class GetVersionsServlet extends ParentServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long docName = Long.parseLong(request.getParameter("document"));
            request.getSession().setAttribute("documentToView", docName);
            List<VersionBean> vers = service.getVersionsOfDocument(requestParser.getAuthorBean(request).getLogin(), docName);
            showVersions(vers, request, response);
        } catch (SystemException e) {
            throw new ServletException(e);
        } catch (BusinessException e) {
            if (e.getClass() == NoSuchObjectInDB.class) {
                request.setAttribute("versmessage", "message.haveNotAnyVersion");
                RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/AllVersions");
                reqDispatcher.forward(request, response);
            } else {
                throw new ServletException(e);
            }
        }
    }

    private void showVersions(List<VersionBean> vers, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("versionList", vers);
        RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/AllVersions");
        reqDispatcher.forward(request, response);

    }
}
