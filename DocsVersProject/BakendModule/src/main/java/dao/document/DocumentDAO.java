package dao.document;

import dao.DAO;
import entities.Document;
import exception.DAOException;
import exception.SystemException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 07.02.13
 * Time: 10:45
 * To change this template use File | Settings | File Templates.
 */
public interface DocumentDAO extends DAO {
    public void addDocument(Document document) throws DAOException, SystemException;

    public List<Document> getDocumentsByAuthorID(long id) throws DAOException, SystemException;

    void editDocumentDescription(String login, String docName, String newDescription) throws DAOException, SystemException;

    void deleteDocument(String login, long docNameCode) throws DAOException, SystemException;

    Document getDocumentByAuthorAndName(String login, long docNameCode) throws DAOException, SystemException;

    long getDocumentID(String login, long docName) throws DAOException, SystemException;
}
