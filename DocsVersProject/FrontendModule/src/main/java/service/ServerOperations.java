package service;

import exception.NullFileException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 01.03.13
 * Time: 9:38
 * To change this template use File | Settings | File Templates.
 */
public class ServerOperations {
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
        String name = FileNameGenerator.generateName(versName);
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
        // Location to save data that is larger than maxMemSize.
        //todo: abs path
        factory.setRepository(new File("C:\\temp"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);
        return upload;
    }

    public File storeFile(FileItem fi, String path, String newFileName) throws Exception {
        File file;
        // Get the uploaded file parameters
        String fileName = fi.getName();
        long sizeInBytes = fi.getSize();

        if (fileName.isEmpty() && sizeInBytes == 0 || newFileName.isEmpty()) {
            throw new NullFileException("No file to upload.");
        } else {

            // Write the file
            String pathToFile = path + newFileName;
            file = new File(pathToFile);
            fi.write(file);
            return file;
        }
    }

}
