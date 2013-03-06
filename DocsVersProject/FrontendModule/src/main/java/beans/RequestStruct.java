package beans;

import org.apache.commons.fileupload.FileItem;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 06.03.13
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
public class RequestStruct {
        private FileItem fileItem;
        private String description;
        private long documentName;
        private AuthorBean authorBean;

        public RequestStruct(FileItem fileItem, String description, long documentName, AuthorBean authorBean){
            this.fileItem = fileItem;
            this.description = description;
            this.documentName = documentName;
            this.authorBean = authorBean;
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

        public long getDocumentName() {
            return documentName;
        }

        public void setDocumentName(long documentName) {
            this.documentName = documentName;
        }

        public AuthorBean getAuthorBean() {
            return authorBean;
        }

        public void setAuthorBean(AuthorBean authorBean) {
            this.authorBean = authorBean;
        }

}
