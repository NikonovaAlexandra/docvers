package servlets.versionServlet;

import beans.AuthorBean;
import beans.VersionBean;
import exception.MyException;
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
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */
public class ViewServlet extends ParentServlet {
    private static final String urlView = "/View";
    private static final String urlEdit = "/Edit";
    private static final String urlReq1 = "/ViewVersion";
    private static final String urlReq2 = "/GetEditVersion";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            VersionBean versionBean = parseVersionBean(request);
            if (request.getRequestURI().equals(urlReq1)) {
                String decsr = versionBean.getDescription();
                versionBean.setDescription(WikiTextInterpretator.convertWikitextStringToString(decsr));
                showMessage(request, response, versionBean, "version", urlView);
            } else {
                showMessage(request, response, versionBean, "version", urlEdit);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

}
