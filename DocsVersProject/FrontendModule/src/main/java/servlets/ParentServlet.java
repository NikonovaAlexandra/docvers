package servlets;

import service.DBOperations;
import service.RequestParser;
import service.ServerOperations;

import javax.servlet.http.HttpServlet;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 05.03.13
 * Time: 12:17
 * To change this template use File | Settings | File Templates.
 */
public class ParentServlet extends HttpServlet {
    protected DBOperations service;

    public long getDocumentName() {
        return documentName;
    }

    public void setDocumentName(long documentName) {
        this.documentName = documentName;
    }

    private long documentName;
    protected ServerOperations serverService;
    protected String filePath;
    protected RequestParser requestParser;

    public void init() {
        requestParser = new RequestParser();
        service = new DBOperations();
        serverService = new ServerOperations();
        // Get the file location where it would be stored.
        filePath =
                getServletContext().getInitParameter("file-upload");
    }
}
