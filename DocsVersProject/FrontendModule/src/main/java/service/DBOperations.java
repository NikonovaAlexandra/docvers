
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

    public void addDocument(DocumentBean documentBean) throws BusinessException, SystemException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
//            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            DocumentDAO documentDAO = DAOFactory.getInstance().getDocumentDAO(conn);
            documentDAO.addDocument(Converter.convertDocumentBeanToDocument(documentBean));
            conn.commit();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if (conn != null) {
                connPool.free(conn);
            }

        }
    }
    public long getLastVersionNameInfo(long docID) throws BusinessException, SystemException {
            Connection conn = null;
            ConnectionPool connPool = null;
            try {
                connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
                conn = connPool.getConnection();
//                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                VersionDAO versionDAO = DAOFactory.getInstance().getVersionDAO(conn);
                long id = versionDAO.getLastVersionNameInfo(docID);
                conn.commit();
                return id;
            } catch (SQLException e) {
                throw new DAOException(e);

            } finally {
                if (conn != null) {
                    connPool.free(conn);
                }
            }
    }

    public void addVersion(VersionBean versionBean) throws BusinessException, SystemException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
//            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            VersionDAO versionDAO = DAOFactory.getInstance().getVersionDAO(conn);
            versionDAO.addVersion(Converter.convertVersionBeanToVersion(versionBean));
            conn.commit();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if (conn != null) {
                connPool.free(conn);
            }

        }
    }

    public List<DocumentBean> getDocumentsByAuthor(String login) throws BusinessException, SystemException {
        List<DocumentBean> documentBeans = new ArrayList<DocumentBean>();
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
//            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
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
            if (conn != null) {
                connPool.free(conn);
            }
        }


    }

    public DocumentBean getDocumentsByAuthorAndName(String login, long docNameCode) throws BusinessException, SystemException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
//            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(conn);
            Document doc = dao.getDocumentByAuthorAndName(login, docNameCode);
            conn.commit();
            AuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO(conn);
            Author author = authorDAO.getAuthorByLogin(login);
            DocumentBean documentBean = Converter.convertDocumentToDocumentBean(doc, author);
            return documentBean;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if (conn != null) {
                connPool.free(conn);
            }
        }


    }

    public AuthorBean getAuthorByLogin(String login) throws BusinessException, SystemException {
        Author author;
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
//            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            AuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO(conn);
            author = authorDAO.getAuthorByLogin(login);
            conn.commit();
            return Converter.convertAuthorToAuthorBean(author);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if (conn != null) {
                connPool.free(conn);
            }

        }
    }

    public List<VersionBean> getVersionsOfDocument(String login, long docNameCode) throws BusinessException, SystemException {
        List<VersionBean> versionBeans = new ArrayList<VersionBean>();
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
//            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            DocumentDAO docDAO = DAOFactory.getInstance().getDocumentDAO(conn);
            Document doc = docDAO.getDocumentByAuthorAndName(login, docNameCode);
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
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if (conn != null) {
                connPool.free(conn);
            }
        }


    }

    public VersionBean getVersion(String login, long docNameCode, long versName) throws BusinessException, SystemException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
//            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            DocumentDAO docDAO = DAOFactory.getInstance().getDocumentDAO(conn);
            Document doc = docDAO.getDocumentByAuthorAndName(login, docNameCode);

            VersionDAO dao = DAOFactory.getInstance().getVersionDAO(conn);
            Version ver = dao.getVersion(doc.getId(), versName);

            AuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO(conn);
            Author authorDoc = authorDAO.getAuthorByLogin(login);
            conn.commit();
            Author authorVers = authorDAO.getAuthorByID(ver.getAuthorID());
            VersionBean versionBean = Converter.convertVersionToVersionBean(ver, doc, authorDoc, authorVers);

            conn.commit();
            return versionBean;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if (conn != null) {
                connPool.free(conn);
            }
        }


    }


    public void deleteDocument(String login, long docNameCode) throws BusinessException, SystemException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
//            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            DocumentDAO docDAO = DAOFactory.getInstance().getDocumentDAO(conn);
            docDAO.deleteDocument(login, docNameCode);
            conn.commit();
        } catch (SQLException e) {
            throw new DAOException(e);

        } finally {
            if (conn != null) {
                connPool.free(conn);
            }
        }
    }

    public void deleteVersion(long versName, long docCode, String login) throws BusinessException, SystemException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
//            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            VersionDAO verDAO = DAOFactory.getInstance().getVersionDAO(conn);
            verDAO.deleteVersion(versName, docCode, login);
            conn.commit();
        } catch (SQLException e) {
            throw new DAOException(e);

        } finally {
            if (conn != null) {
                connPool.free(conn);
            }
        }

    }

    public long getDocumentIDByCodeNameAndLogin(String login, long docName)throws BusinessException, SystemException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
//            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            DocumentDAO docDAO = DAOFactory.getInstance().getDocumentDAO(conn);
            long id = docDAO.getDocumentID(login, docName);
            conn.commit();
            return id;
        } catch (SQLException e) {
            throw new DAOException(e);

        } finally {
            if (conn != null) {
                connPool.free(conn);
            }
        }
    }

    public String getVersionType(long versionName, long documentName, String login) throws BusinessException, SystemException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
 //           conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            VersionDAO verDAO = DAOFactory.getInstance().getVersionDAO(conn);
            String type = verDAO.getVersionType(versionName, documentName, login);
            conn.commit();
            return type;
        } catch (SQLException e) {
            throw new DAOException(e);

        } finally {
            if (conn != null) {
                connPool.free(conn);
            }
        }
    }
}
