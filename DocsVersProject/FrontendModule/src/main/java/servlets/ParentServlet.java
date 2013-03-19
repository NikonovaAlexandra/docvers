package servlets;

import service.FileFolderService;
import service.dbOperations.DBOperations;
import service.dbOperations.DBOperationsFactory;
import service.RequestParser;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 05.03.13
 * Time: 12:17
 * To change this template use File | Settings | File Templates.
 */
//todo language 2 click
//buttons
public class ParentServlet extends HttpServlet {
    private DBOperations service;
    //todo check if work on 2 users
    private long documentName;
    private FileFolderService fileFolderService;
    private String filePath;
    private RequestParser requestParser;
    private String encoding;

    public void init() {
        requestParser = new RequestParser();
        service = DBOperationsFactory.getDBService();;
        fileFolderService = new FileFolderService();
        // Get the file location where it would be stored.

        filePath =
                getServletContext().getInitParameter("file-upload");
        encoding = getServletContext().getInitParameter("encoding");
    }

    public long getDocumentName() {
        return documentName;
    }

    public void setDocumentName(long documentName) {
        this.documentName = documentName;
    }

    public String getFilePath() {
        return filePath;
    }

    public DBOperations getService() {
        return service;
    }

    public FileFolderService getFileFolderService() {
        return fileFolderService;
    }

    public RequestParser getRequestParser() {
        return requestParser;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        throw new ServletException("GET method used with " +
                getClass().getName() + ": POST method required.");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        throw new ServletException("POST method used with " +
                getClass().getName() + ": GET method required.");
    }


    public void showMessage(HttpServletRequest request, HttpServletResponse response, Object message, String parameterName, String url)
            throws ServletException, IOException {
        request.setAttribute(parameterName, message);
        RequestDispatcher reqDispatcher = getServletConfig().getServletContext().
                getRequestDispatcher(url);
        reqDispatcher.forward(request, response);
    }


}
