package servlets.versionServlet;

import beans.AuthorBean;
import beans.UploadVersionRequestStruct;
import beans.VersionBean;
import exception.NullFileException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.VersionUploadRequestParser;
import servlets.ParentServlet;

import javax.servlet.ServletContext;
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
    private static final String messageName = "uploadmessage";
    private static final String url = "/AddVersion";
    private VersionUploadRequestParser requestParser;
    private UploadVersionRequestStruct struct;
    private ServletContext context;
    public void init() {
        super.init();
        requestParser = new VersionUploadRequestParser();
        context = getServletContext();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        //todo check if ex while storage => del row from db
        String message;

        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            message = "No file uploaded";
            showMessage(request, response, message, messageName, url);
        } else
        {
            try {

                VersionBean versionBean = parseRequestToUpload(request);
                setDocumentName(versionBean.getDocument().getCodeDocumentName());
                FileItem fi = struct.getFileItem();
                if (versionBean != null) {
                    getFileFolderService().storeFile(fi, versionBean.getPath());
                }
                getService().addVersion(versionBean);
                response.sendRedirect("/Versions?document=" + getDocumentName());
            } catch (Exception e) {
                if (e.getClass() == FileUploadBase.SizeLimitExceededException.class) {
                    showMessage(request, response, "message.tooBigFile", messageName, url);
                } else if (e.getClass() == NullFileException.class) {
                    showMessage(request, response, "message.noFileToUpload", messageName, url);
                } else {
                    throw new ServletException(e);
                }
            }
        }

    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect(url + "?language=" + request.getParameter("language"));
    }

    private VersionBean parseRequestToUpload(HttpServletRequest request) throws Exception {
        long documentCode = (Long) request.getSession().getAttribute("documentToView");
        AuthorBean authorBean = requestParser.getAuthorBean(request);
        struct = requestParser.geVersionDescriptionAndFileItem(request);
        String descr = struct.getDescription();
        FileItem fi = struct.getFileItem();
        VersionBean versionBean = requestParser.getVersionBean(authorBean, documentCode, fi.getName(), descr, context);
        return versionBean;
    }
}