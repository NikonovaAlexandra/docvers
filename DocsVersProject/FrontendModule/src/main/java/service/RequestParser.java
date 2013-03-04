package service;

import beans.AuthorBean;
import beans.DocumentBean;
import beans.VersionBean;
import entities.Author;
import exception.NullFileException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Date;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 17.02.13
 * Time: 16:39
 * To change this template use File | Settings | File Templates.
 */
public class RequestParser {
    private static RequestParser instance;
   // todo : why singleton?

    public static synchronized RequestParser getInstance() {
        if (instance == null) {
            instance = new RequestParser();
        }
        return instance;
    }

    public AuthorBean getAuthorBean(HttpServletRequest request) {
        // AuthorBean a = new AuthorBean("author2", "pass2");
        return (AuthorBean) request.getSession().getAttribute("user");

    }

    public DocumentBean getDocumentBean(HttpServletRequest request) {
        AuthorBean author = getAuthorBean(request);
        String name = request.getParameter("docname");
        String description = request.getParameter("docdescription");
        return new DocumentBean(author, name, description);
    }

    public VersionBean getVersionBeanAndStoreFile(HttpServletRequest request, String filePath) throws Exception {
        DBOperations operations = DBOperations.getInstance();
        File file;
        VersionBean versionBean = new VersionBean();

        String authorLogin = RequestParser.getInstance().getAuthorBean(request).getLogin();
        AuthorBean author = operations.getAuthorByLogin(authorLogin);
        versionBean.setAuthor(author);
        ServletFileUpload upload = ServerOperations.getInstance().initUpload(request);


        // Parse the request to get file items.
        List fileItems = upload.parseRequest(request);

        // Process the uploaded file items
        Iterator i = fileItems.iterator();
        String docName = null;
        while (i.hasNext()) {
            FileItem fi = (FileItem) i.next();
            if (fi.isFormField()) {
                if (fi.getFieldName().equals("document")) {
                    docName = fi.getString();
                }
                if (fi.getFieldName().equals("versdescription")) {
                    versionBean.setDescription(fi.getString());
                }

            } else {
                // Get the uploaded file parameters
                String fieldName = fi.getFieldName();
                String fileName = fi.getName();
                String contentType = fi.getContentType();
                boolean isInMemory = fi.isInMemory();
                long sizeInBytes = fi.getSize();

                if (fileName.isEmpty() && sizeInBytes == 0) {
                    throw new NullFileException("You did not select a file.");
                } else {
                    // Write the file

                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new File(filePath + authorLogin + "/" + docName + "/" +
                                fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new File(filePath + authorLogin + "/" + docName + "/" +
                                fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }

                    fi.write(file);
                    versionBean.setPath(file.getAbsolutePath());
                    DocumentBean documentBean = operations.getDocumentsByAuthorAndName(authorLogin, docName);
                    versionBean.setDocument(documentBean);
                    versionBean.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
                }
            }
        }
        return versionBean;
    }
}
