package servlets.versionServlet;

import beans.VersionBean;
import exception.NullFileException;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.VersionRequestParser;
import servlets.ParentServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 01.03.13
 * Time: 9:03
 * To change this template use File | Settings | File Templates.
 */

public class UploadServlet extends ParentServlet {

    private boolean isMultipart;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        String message;
        VersionRequestParser requestParser = new VersionRequestParser();
        if (!isMultipart) {
            message = "No file uploaded";
            showMessage(request, response, message);
        } else {
            try {
                setDocumentName((Long) request.getSession().getAttribute("documentToView"));
                VersionBean versionBean = requestParser.getVersionBeanAndStoreFile(request, filePath);
                service.addVersion(versionBean);
                response.sendRedirect("/Versions?document=" + getDocumentName());
            } catch (Exception e) {
                if (e.getClass() == FileUploadBase.SizeLimitExceededException.class) {
                    showMessage(request, response, "message.tooBigFile");
                }

                if (e.getClass() == NullFileException.class) {
                    showMessage(request, response, "message.noFileToUpload");
                } else {
                    throw new ServletException(e);
                }
            }
        }

    }

    private void showMessage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("uploadmessage", message);
        RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/AddVersion?document=");
        reqDispatcher.forward(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        throw new ServletException("GET method used with " +
                getClass().getName() + ": POST method required.");
    }
}