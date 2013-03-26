package servlets.versionServlet;

import beans.AuthorBean;
import beans.VersionBean;
import exception.BusinessException;
import exception.MyException;
import exception.SystemException;
import service.FileNameGenerator;
import servlets.ParentServlet;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

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

        try {
            VersionBean versionBean = parse(request);
            setDocumentName(versionBean.getDocument().getCodeDocumentName());
            file = new File(versionBean.getPath());
            String newFileName = FileNameGenerator.generateDownloadVersionName(versionBean.getDocument().getName(),
                    versionBean.getVersionName(), versionBean.getVersionType());
            ServletOutputStream outStream = response.getOutputStream();
            setRespose(response, newFileName);

            getFileFolderService().downloadFile(file.getAbsolutePath(), outStream, BUFSIZE);
            outStream.flush();

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

    private void setRespose(HttpServletResponse response, String fileName) {
        ServletContext context = getServletConfig().getServletContext();
        String mimetype = context.getMimeType(file.getAbsolutePath());

        // sets response content type
        if (mimetype == null) {
            mimetype = "application/octet-stream";
        }
        response.setContentType(mimetype);
        response.setContentLength((int) file.length());

        // sets HTTP header
        response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
    }

}
