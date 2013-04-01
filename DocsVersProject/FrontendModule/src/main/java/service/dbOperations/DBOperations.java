package service.dbOperations;

import beans.AuthorBean;
import beans.DocumentBean;
import beans.VersionBean;
import exception.MyException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 18.03.13
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */
public interface DBOperations {
    public void addDocument(DocumentBean documentBean) throws MyException;

    public long getLastVersionNameInfo(long docID) throws MyException;

    public void addVersion(VersionBean versionBean) throws MyException;

    public List<DocumentBean> getDocumentsByAuthor(String login) throws MyException;

    public DocumentBean getDocumentByAuthorAndName(String login, long docNameCode) throws MyException;

    public AuthorBean getAuthorByLogin(String login) throws MyException;

    public List<VersionBean> getVersionsOfDocument(String login, long docNameCode) throws MyException;

    public VersionBean getVersion(String login, long docNameCode, long versName) throws MyException;

    public void deleteDocument(String login, long docNameCode) throws MyException;

    public void deleteVersion(long versName, long docCode, String login) throws MyException;

    public long getDocumentIDByCodeNameAndLogin(String login, long docName) throws MyException;

    public String getVersionType(long versionName, long documentName, String login) throws MyException;

    public void editVersionDescription(VersionBean versionBean) throws MyException;

    public void editDocumentDescription(DocumentBean documentBean) throws MyException;
}
