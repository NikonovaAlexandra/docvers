package service;

import beans.AuthorBean;
import beans.DocumentBean;
import beans.VersionBean;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 17.02.13
 * Time: 16:39
 * To change this template use File | Settings | File Templates.
 */
public class RequestParser {
    private char separator = File.separatorChar;

    public AuthorBean getAuthorBean(HttpServletRequest request) {
       // return new AuthorBean(3, "author2", "pass2");
        return (AuthorBean) request.getSession().getAttribute("user");
    }

    public DocumentBean getDocumentBean(HttpServletRequest request) {
        AuthorBean author = getAuthorBean(request);
        String name = request.getParameter("docname");
        String description = request.getParameter("docdescription");
        return new DocumentBean(author, name, description, name.hashCode());
    }

    public VersionBean getVersionBean(AuthorBean authorBean, DocumentBean documentBean, String description,
                                      Date date, String path, String versionType) {
        return new VersionBean(authorBean, documentBean, description, date, path, versionType);
    }





}
