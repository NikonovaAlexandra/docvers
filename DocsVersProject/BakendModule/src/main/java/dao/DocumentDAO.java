package dao;

import entities.Author;
import entities.Document;
import entities.Version;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 07.02.13
 * Time: 10:45
 * To change this template use File | Settings | File Templates.
 */
public interface DocumentDAO {
    public void addDocument(Document document) throws SQLException;
    public void deleteDocument(Document document) throws SQLException;
    public List<Document> getAllDocuments();
    public Author getAuthorByDocument(Document document);
    public List<Document> getDocumentsByAuthor(Author author);
    public List<Version> getVersionsOfDocument(Document document);

//    public void addVersion(Version version);
//    public void addAuthor(Author author);
//    public List<Author> getAllAuthors();
//    public Author getAuthorByID(long id);
//    public Author getAuthorByLogin(String login);
//    public Author getAuthorByVersion(Version version);
//    public List<Version> getVersionsByAuthor(Author author);
}
