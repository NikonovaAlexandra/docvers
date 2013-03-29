package servlets.versionServlet;

import beans.DocumentBean;
import beans.VersionBean;
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
 * Date: 26.02.13
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
public class GetVersionsServlet extends ParentServlet {
    private static final String messageName = "versmessage";
    private static final String url = "/AllVersions";

    //todo released

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String par = request.getParameter("document");
            long docName = 0;
            if (!(par == null)) {
                docName = Long.parseLong(par);
            }
            if (docName == 0) {
                docName = (Long) request.getSession().getAttribute("documentToView");
            } else {
                request.getSession().setAttribute("documentToView", docName);
            }
            List<VersionBean> vers = getService().getVersionsOfDocument(getRequestParser().getAuthorBean(request).getLogin(), docName);
            for(VersionBean ver: vers) {
                String description = WikiTextInterpretator.convertWikitextStringToString(ver.getDescription());
                ver.setDescription(description);
            }
            showMessage(request, response, vers, "versionList", url);
        } catch (SystemException e) {
            throw new ServletException(e);
        } catch (BusinessException e) {
            if (e.getClass() == NoSuchObjectInDB.class) {
                showMessage(request, response, "message.haveNotAnyVersion", messageName, url);
            } else {
                throw new ServletException(e);
            }
        } catch (MyException e) {
            throw new ServletException(e);
        }
    }

}
