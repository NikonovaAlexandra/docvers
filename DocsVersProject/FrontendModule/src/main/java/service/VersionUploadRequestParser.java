package service;

import beans.AuthorBean;
import beans.DocumentBean;
import beans.UploadVersionRequestStruct;
import beans.VersionBean;
import dao.DAOType;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.dbOperations.DBOperations;
import service.dbOperations.DBOperationsFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
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
public class VersionUploadRequestParser extends RequestParser {

    public UploadVersionRequestStruct geVersionDescriptionAndFileItem(HttpServletRequest request) throws Exception {
        FileFolderService fileFolderOperations = new FileFolderService();
        ServletFileUpload upload = fileFolderOperations.initUpload(request);

        FileItem fileItem = null;
        String description = null;
        // Parse the request to get file items.
        List fileItems = upload.parseRequest(request);

        // Process the uploaded file items
        Iterator i = fileItems.iterator();

        while (i.hasNext()) {
            FileItem fi = (FileItem) i.next();
            if (fi.isFormField() && fi.getFieldName().equals("versdescription")) {
                description = fi.getString();
            } else {

                fileItem = fi;
            }
        }
        return new UploadVersionRequestStruct(fileItem, description);
    }

    public VersionBean getVersionBean(AuthorBean authorBean, long documentCode, String versName, String descriprion, ServletContext context) throws Exception {

        DBOperations operations = DBOperationsFactory.getDBService((DAOType) context.getAttribute("type"));
        char separator = File.separatorChar;
        String login = authorBean.getLogin();
        String newFilePath = context.getAttribute("filePath") + login + separator
                + documentCode + separator;

        long docID = operations.getDocumentIDByCodeNameAndLogin(login, documentCode);

        long lastVersionName = operations.getLastVersionNameInfo(docID);

        String type = FileNameGenerator.getType(versName);
        String newVersionName = FileNameGenerator.generateUploadVersionName(lastVersionName + 1);
        String newFileName = newVersionName + "." + type;

        DocumentBean documentBean = operations.getDocumentsByAuthorAndName(login,
                documentCode);
        Timestamp date = new Timestamp(Calendar.getInstance().getTimeInMillis());
        VersionBean versionBean = getVersionBean(authorBean, documentBean,
                descriprion, date, newFilePath + newFileName, type);

        return versionBean;

    }

}
