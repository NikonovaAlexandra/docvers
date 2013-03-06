package service;

import beans.AuthorBean;
import beans.DocumentBean;
import beans.RequestStruct;
import beans.VersionBean;
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
 * User: alni
 * Date: 06.03.13
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
public class VersionRequestParser extends RequestParser{
    public RequestStruct getRequestFields(HttpServletRequest request) throws Exception {
        long docName = (Long)request.getSession().getAttribute("documentToView");
        AuthorBean author = getAuthorBean(request);
        String description = null;
        FileItem fileItem = null;

        ServerOperations serverOperations = new ServerOperations();
        ServletFileUpload upload = serverOperations.initUpload(request);

        // Parse the request to get file items.
        List fileItems = upload.parseRequest(request);

        // Process the uploaded file items
        Iterator i = fileItems.iterator();

        while (i.hasNext()) {
            FileItem fi = (FileItem) i.next();
            if (fi.isFormField()) {

                if (fi.getFieldName().equals("versdescription")) {
                    description = fi.getString();
                }

            } else {
               fileItem = fi;
            }
        }
        return new RequestStruct(fileItem, description, docName, author);
    }


    public VersionBean getVersionBeanAndStoreFile(HttpServletRequest request, String filePath) throws Exception {
        DBOperations operations = new DBOperations();
        ServerOperations serverOperations = new ServerOperations();
        File file = null;
        RequestStruct requestStruct = getRequestFields(request);
        FileItem fi = requestStruct.getFileItem();
        AuthorBean author = requestStruct.getAuthorBean();
        String fileName = fi.getName();
        char separator = File.separatorChar;
        String login = author.getLogin();
        long docName = requestStruct.getDocumentName();
        String newFilePath = filePath + login + separator
                + docName + separator;

        long docID = operations.getDocumentIDByCodeNameAndLogin(login, docName);

        long lastVersionName = operations.getLastVersionNameInfo(docID);
        String versionName = FileNameGenerator.generateName(lastVersionName + 1);
        String type = FileNameGenerator.getType(fileName);
        String newFileName = versionName + "." + type;
        if (type != null) {
            file = serverOperations.storeFile(fi, newFilePath, newFileName);
        }
        if (file != null) {
            DocumentBean documentBean = operations.getDocumentsByAuthorAndName(login,
                    docName);
            Date date = new Date(Calendar.getInstance().getTimeInMillis());
            VersionBean versionBean = getVersionBean(author, documentBean,
                    requestStruct.getDescription(), date, file.getAbsolutePath(), type);
            return versionBean;

        } else return null;
    }



}
