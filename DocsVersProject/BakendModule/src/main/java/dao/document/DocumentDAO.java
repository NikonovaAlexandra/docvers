package dao.document;

import dao.DAO;
import entities.Document;
import exception.DAOException;
import exception.NoSuchObjectInDB;
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

    public void deleteDocument(long id) throws DAOException, SystemException;

    public List<Document> getAllDocuments() throws DAOException, SystemException;

    public List<Document> getDocumentsByAuthorID(long id) throws DAOException, SystemException;

//    public void addVersion(Version VersionDAOTest);
//    public void addAuthor(Author AuthorDAOTest);
//    public List<Author> getAllAuthors();

//    public Author getAuthorByLogin(String login);
//    public Author getAuthorByVersion(Version VersionDAOTest);
//    public List<Version> getVersionsByAuthor(Author AuthorDAOTest);

    Document getDocumentByAuthorAndName(String login, String docName) throws DAOException, SystemException;

    void editDocumentDescription(String login, String docName, String newDescription) throws DAOException, SystemException;

    void deleteDocument(String login, String docName) throws DAOException, SystemException;
}
