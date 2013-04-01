package servlets.versionServlet;

import beans.AuthorBean;
import beans.VersionBean;
import exception.BusinessException;
import exception.MyException;
import exception.ObjectAlreadyExistsException;
import servlets.ParentServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 30.03.13
 * Time: 19:11
 * To change this template use File | Settings | File Templates.
 */
public class EditServlet extends ParentServlet {
    private static final String url = "/GetEditVersion";
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        VersionBean versionBean = null;
        try {
            versionBean = parseVersionBean(request);
            String description = request.getParameter("description");


            if ( !versionBean.getDescription().equals(description)) {
                versionBean.setDescription(description);
                getService().editVersionDescription(versionBean);
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

