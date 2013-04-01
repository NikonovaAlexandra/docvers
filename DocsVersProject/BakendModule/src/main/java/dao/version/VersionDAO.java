package dao.version;

import dao.DAO;
import entities.Version;
import exception.DAOException;
import exception.MyException;
import exception.SystemException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 12.02.13
 * Time: 9:05
 * To change this template use File | Settings | File Templates.
 */
public interface VersionDAO extends DAO {
    public List<Version> getVersionsOfDocument(long id) throws MyException;

    public void addVersion(Version version) throws MyException;

    long getLastVersionNameInfo(long docID) throws MyException;

    void deleteVersion(long id, long docCode, String login) throws MyException;

    String getVersionType(long versionName, long documentName, String login) throws MyException;

    Version getVersion(long id, long versName) throws MyException;
    public void updateVersionDescription(String login, long codeDocName, long versionName, String description) throws MyException;
}
