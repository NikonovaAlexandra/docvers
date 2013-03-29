package servlets.versionServlet;

import beans.AuthorBean;
import beans.VersionBean;
import exception.MyException;
import service.FileNameGenerator;
import servlets.ParentServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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


        try {
            String param = request.getParameter("param");
            VersionBean versionBean = parse(request);
            if (param == null) {
                setDocumentName(versionBean.getDocument().getCodeDocumentName());
                file = new File(versionBean.getPath());
                String newFileName = FileNameGenerator.generateDownloadVersionName(versionBean.getDocument().getName(),
                        versionBean.getVersionName(), versionBean.getVersionType());
                ServletOutputStream outStream = response.getOutputStream();
                setRespose(response, newFileName);

                getFileFolderService().downloadFile(file.getAbsolutePath(), outStream, BUFSIZE);
                outStream.flush();
            } else {
                StringSelection stringSelection = new StringSelection(request.getRequestURL() + "?" +
                        request.getQueryString().replaceFirst("(&)(param)(=.*)",""));
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                        stringSelection, null);
                String url = "/Versions?document=" + versionBean.getDocument().getCodeDocumentName();
                showMessage(request, response, "message.CopyToClipboard", "versmessage", url);

            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private VersionBean parse(HttpServletRequest request) throws MyException {
        long id = Long.parseLong(request.getParameter("version"));
        String login = request.getParameter("author");
        Long documentCode = Long.parseLong(request.getParameter("codeDocument"));
        //long documentCode = (Long) request.getSession().getAttribute("documentToView");
        AuthorBean authorBean = getRequestParser().getAuthorBean(request);
        VersionBean versionBean = getService().getVersion(login, documentCode, id);
        return versionBean;
    }

    private void setRespose(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        ServletContext context = getServletConfig().getServletContext();
        String mimetype = context.getMimeType(file.getAbsolutePath());

        // sets response content type
        if (mimetype == null) {
            mimetype = "application/octet-stream";
        }
        response.setContentType(mimetype);
        response.setContentLength((int) file.length());
        // sets HTTP header
        //todo russian name
        byte[] bytes = fileName.getBytes("UTF-8");
        String name = new String(bytes,"UTF-8");
        if (mimetype.equals("application/pdf")) {
            response.setHeader("Content-Disposition", "inline; filename=\"" + name + "\"");
        } else {
            response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
        }
    }

}
