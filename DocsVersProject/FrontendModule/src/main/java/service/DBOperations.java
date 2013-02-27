package service;

import beans.AuthorBean;
import beans.Converter;
import beans.DocumentBean;
import beans.VersionBean;
import dao.DAOFactory;
import dao.author.AuthorDAO;
import dao.document.DocumentDAO;
import dao.version.VersionDAO;
import entities.Author;
import entities.Document;
import entities.Version;
import exception.BusinessException;
import exception.DAOException;
import exception.NoSuchObjectInDB;
import exception.SystemException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 17.02.13
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */
public class DBOperations {
    private static DBOperations instance;
    static int i =0;

    public static synchronized DBOperations getInstance() {
        if (instance == null) {
            instance = new DBOperations();
        }
        return instance;
    }

    public void addDocument(DocumentBean documentBean) throws BusinessException, SystemException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            AuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO(conn);
            Author author = authorDAO.getAuthorByLogin(documentBean.getAuthor().getLogin());
            DocumentDAO documentDAO = DAOFactory.getInstance().getDocumentDAO(conn);
            Document doc = new Document();
            doc.setName(documentBean.getName());
            doc.setDescription(documentBean.getDescription());
            doc.setAuthorID(author.getId());
            documentDAO.addDocument(doc);
            conn.commit();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if (conn != null)
                connPool.free(conn);

        }
    }

    public List<DocumentBean> getAllDocuments() throws BusinessException, SystemException {
        List<DocumentBean> documentBeans = new ArrayList<DocumentBean>();
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(conn);
            List<Document> docs = dao.getAllDocuments();
            conn.commit();
            connPool.free(conn);
            conn = connPool.getConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            AuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO(conn);
            for (Document doc : docs) {
                Author author = authorDAO.getAuthorByID(doc.getAuthorID());
                DocumentBean documentBean = Converter.convertDocumentToDocumentBean(doc, author);
                documentBeans.add(documentBean);
            }
            conn.commit();
            return documentBeans;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if (conn != null)
                connPool.free(conn);

        }

    }

    public List<DocumentBean> getDocumentsByAuthor(String login) throws BusinessException, SystemException {
        List<DocumentBean> documentBeans = new ArrayList<DocumentBean>();
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            AuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO(conn);
            Author author = authorDAO.getAuthorByLogin(login);
            DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(conn);
            List<Document> docs = dao.getDocumentsByAuthorID(author.getId());
            conn.commit();
            for (Document doc : docs) {
                Author a = authorDAO.getAuthorByID(doc.getAuthorID());
                DocumentBean documentBean = Converter.convertDocumentToDocumentBean(doc, a);
                documentBeans.add(documentBean);
            }
            conn.commit();
            return documentBeans;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if (conn != null)
                connPool.free(conn);
        }


    }

    public Author getAuthorByLogin(String login) throws BusinessException, SystemException {
        Author author;
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            AuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO(conn);
            author = authorDAO.getAuthorByLogin(login);
            conn.commit();
            return author;
        } catch (SQLException e) {
            throw new DAOException(e);
        }  finally {
            if (conn != null)
                connPool.free(conn);

        }
    }

    public List<VersionBean> getVersionsOfDocument(String login, String docName) throws BusinessException, SystemException {
        List<VersionBean> versionBeans = new ArrayList<VersionBean>();
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            DocumentDAO docDAO = DAOFactory.getInstance().getDocumentDAO(conn);
            Document doc = docDAO.getDocumentByAuthorAndName(login, docName);
            VersionDAO dao = DAOFactory.getInstance().getVersionDAO(conn);
            List<Version> vers = dao.getVersionsOfDocument(doc.getId());
            AuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO(conn);
            Author authorDoc = authorDAO.getAuthorByLogin(login);
            conn.commit();
            for (Version ver : vers) {
                Author authorVers = authorDAO.getAuthorByID(ver.getAuthorID());
                VersionBean versionBean = Converter.convertVersionToVersionBean(ver, doc, authorDoc, authorVers);
                versionBeans.add(versionBean);
            }
            conn.commit();
            return versionBeans;
        }  catch (SQLException e) {
            throw new DAOException(e);
        }  finally {
            if (conn != null)
                connPool.free(conn);
        }


    }

    public void deleteDocument(String login, String docName) throws BusinessException, SystemException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            DocumentDAO docDAO = DAOFactory.getInstance().getDocumentDAO(conn);
            Document doc = docDAO.getDocumentByAuthorAndName(login, docName);
            docDAO.deleteDocument(doc.getId());
            conn.commit();
        } catch (SQLException e) {
            throw new DAOException(e);

        } finally {
            if (conn != null)
                connPool.free(conn);
        }
    }
}