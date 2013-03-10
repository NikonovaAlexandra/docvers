package servlets.versionServlet;

import beans.AuthorBean;
import beans.UploadVersionRequestStruct;
import beans.VersionBean;
import exception.BusinessException;
import exception.NullFileException;
import exception.SystemException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import service.FileNameGenerator;
import service.ServerOperations;
import servlets.ParentServlet;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 07.03.13
 * Time: 12:32
 * To change this template use File | Settings | File Templates.
 */
public class DownloadVersionServlet extends ParentServlet {

        private static final int BUFSIZE = 4096;
        private File file;
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setCharacterEncoding(getEncoding());
            request.setCharacterEncoding(getEncoding());
            ServerOperations serverOperations = new ServerOperations();
            try{
                VersionBean versionBean = parse(request);
                setDocumentName(versionBean.getDocument().getCodeDocumentName());
                file = new File(versionBean.getPath());
                String newFileName = FileNameGenerator.generateDownloadVersionName(versionBean.getDocument().getName(),
                        versionBean.getVersionName(), versionBean.getVersionType());
                ServletOutputStream outStream = response.getOutputStream();
                setRespose(response, newFileName);
                serverOperations.downloadFile(file.getAbsolutePath(), outStream, BUFSIZE);
            } catch (Exception e){
                throw new ServletException(e);
            }
        }

    private VersionBean parse(HttpServletRequest request) throws SystemException, BusinessException {
        long id = Long.parseLong(request.getParameter("version"));
        long documentCode = (Long) request.getSession().getAttribute("documentToView");
        AuthorBean authorBean = getRequestParser().getAuthorBean(request);
        VersionBean versionBean = getService().getVersion(authorBean.getLogin(), documentCode, id);
        return versionBean;
    }

     private void setRespose(HttpServletResponse response, String fileName) {
         ServletContext context  = getServletConfig().getServletContext();
         String mimetype = context.getMimeType(file.getAbsolutePath());

         // sets response content type
         if (mimetype == null) {
             mimetype = "application/octet-stream";
         }
         response.setContentType(mimetype);
         response.setContentLength((int) file.length());

         // sets HTTP header
         response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
     }

}
