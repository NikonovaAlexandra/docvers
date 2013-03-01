package servlets.versionServlet;

import beans.VersionBean;
import exception.NullFileException;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.DBOperations;
import service.RequestParser;
import service.ServerOperations;

import javax.naming.SizeLimitExceededException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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

public class UploadServlet extends HttpServlet {

    private RequestParser parser = RequestParser.getInstance();
    private boolean isMultipart;
    private String filePath;
    private ServerOperations serverOperations = ServerOperations.getInstance();
    private DBOperations operations = DBOperations.getInstance();

    public void init() {
        // Get the file location where it would be stored.
        filePath =
                getServletContext().getInitParameter("file-upload");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        String message;
        if (!isMultipart) {
            message = "No file uploaded";
            showMessage(request, response, message);
        } else {
            try {
                //serverOperations.uploadFile(request, filePath);
                VersionBean versionBean = parser.getVersionBeanAndStoreFile(request, filePath);
                message = "File was uploaded!";
                operations.addVersion(versionBean);
                showMessage(request, response, message);
            } catch (Exception e) {
               if(e.getClass() == FileUploadBase.SizeLimitExceededException.class) {
                   showMessage(request, response, "File you want to upload is too big.");
               }

               if(e.getClass() == NullFileException.class){
                   showMessage(request, response, e.toString());
               } else {
                   throw new ServletException(e);
               }
            }
        }

    }

    private void showMessage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("uploadMessage", message);
        RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/Versions/AddVersion");
        reqDispatcher.forward(request, response);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, java.io.IOException {

        throw new ServletException("GET method used with " +
                getClass().getName() + ": POST method required.");
    }
}