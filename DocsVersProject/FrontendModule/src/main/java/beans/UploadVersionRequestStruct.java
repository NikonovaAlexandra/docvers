package beans;

import org.apache.commons.fileupload.FileItem;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 06.03.13
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
public class UploadVersionRequestStruct {
    private FileItem fileItem;
    private String description;


    public UploadVersionRequestStruct(FileItem fileItem, String description) {
        this.fileItem = fileItem;
        this.description = description;

    }

    public FileItem getFileItem() {
        return fileItem;
    }

    public void setFileItem(FileItem fileItem) {
        this.fileItem = fileItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
