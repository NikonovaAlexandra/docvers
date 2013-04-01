package service;

import exception.NullFileException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 01.03.13
 * Time: 9:38
 * To change this template use File | Settings | File Templates.
 */
public class FileFolderService {
    private char separator = File.separatorChar;

    public void createUserDocumentFolder(String path, String login, long docName) {
        File dir = new File(path + login);
        if (!dir.exists()) {
            dir.mkdir();
        }

        dir = new File(dir.getAbsolutePath() + separator + docName);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public void deleteUserDocumentFolder(String path, String login, long docName) {
        File dir = new File(path + login + separator + docName);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) { //some JVMs return null for empty dirs
                for (File f : files) {
                    f.delete();
                }
            }
            dir.delete();
        }
    }

    public void deleteUserDocumentVersion(String path, String login, long docName, long versName, String type) {
        String name = FileNameGenerator.generateUploadVersionName(versName);
        File file = new File(path + login + separator + docName + separator + name + "." + type);
        if (file.exists()) {
            file.delete();
        }
    }

    public ServletFileUpload initUpload(HttpServletRequest request) throws Exception {
        ResourceBundle resource =
                ResourceBundle.getBundle("fileStorageConfigure");
        int maxMemSize = Integer.parseInt(resource.getString("maxMemSize"));
        int maxFileSize = Integer.parseInt(resource.getString("maxFileSize"));
        File file = null;

        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);
        return upload;
    }

    public void storeFile(FileItem fi, String path) throws Exception {
        File file;
        // Get the uploaded file parameters
        String fileName = fi.getName();
        long sizeInBytes = fi.getSize();

        if (fileName.isEmpty() && sizeInBytes == 0) {
            throw new NullFileException("No file to upload.");
        } else {
            String[] s = path.replace(String.valueOf(separator), "----").split("----");
            String d = s[s.length - 2];
            long doc = Long.parseLong(d);
            String author = s[s.length - 3];
            String p = path.substring(0, path.length() - d.length() - author.length() - s[s.length - 1].length() - 2);
            createUserDocumentFolder(p, author, doc);
            // Write the file
            file = new File(path);
            fi.write(file);
        }
    }

    public void downloadFile(String filePath, ServletOutputStream outputStream, int BUFSIZE) throws IOException {

        File file = new File(filePath);
        int length = 0;
        byte[] byteBuffer = new byte[BUFSIZE];
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        // reads the file's bytes and writes them to the response stream
        while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
            outputStream.write(byteBuffer, 0, length);
        }

        in.close();
        outputStream.close();
    }

}
