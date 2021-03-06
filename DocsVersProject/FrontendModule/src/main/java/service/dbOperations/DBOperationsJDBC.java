
package service.dbOperations;

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
import exception.DAOException;
import exception.MyException;
import exception.SystemException;
import db.ConnectionPool;
import db.ConnectionPoolFactory;

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
public class DBOperationsJDBC implements DBOperations {

    public void addDocument(DocumentBean documentBean) throws MyException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setAutoCommit(false);
            DocumentDAO documentDAO = DAOFactory.getInstance().getDocumentDAO(conn);
            documentDAO.addDocument(Converter.convertDocumentBeanToDocument(documentBean));
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            throw new DAOException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            if (conn != null) {
                connPool.free(conn);
            }

        }
    }

    public long getLastVersionNameInfo(long docID) throws MyException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setAutoCommit(false);
            VersionDAO versionDAO = DAOFactory.getInstance().getVersionDAO(conn);
            long id = versionDAO.getLastVersionNameInfo(docID);
            conn.commit();
            return id;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            throw new DAOException(e);

        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            if (conn != null) {
                connPool.free(conn);
            }
        }
    }

    public void addVersion(VersionBean versionBean) throws MyException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setAutoCommit(false);
            VersionDAO versionDAO = DAOFactory.getInstance().getVersionDAO(conn);
            versionDAO.addVersion(Converter.convertVersionBeanToVersion(versionBean));
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            throw new DAOException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            if (conn != null) {
                connPool.free(conn);
            }

        }
    }

    public List<DocumentBean> getDocumentsByAuthor(String login) throws MyException {
        List<DocumentBean> documentBeans = new ArrayList<DocumentBean>();
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setAutoCommit(false);
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
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            throw new DAOException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            if (conn != null) {
                connPool.free(conn);
            }
        }


    }

    public DocumentBean getDocumentByAuthorAndName(String login, long docNameCode) throws MyException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setAutoCommit(false);
            DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(conn);
            Document doc = dao.getDocumentByAuthorAndName(login, docNameCode);
            conn.commit();
            AuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO(conn);
            Author author = authorDAO.getAuthorByLogin(login);
            DocumentBean documentBean = Converter.convertDocumentToDocumentBean(doc, author);
            return documentBean;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            throw new DAOException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            if (conn != null) {
                connPool.free(conn);
            }
        }


    }

    public AuthorBean getAuthorByLogin(String login) throws MyException {
        Author author;
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setAutoCommit(false);
            AuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO(conn);
            author = authorDAO.getAuthorByLogin(login);
            conn.commit();
            return Converter.convertAuthorToAuthorBean(author);
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            throw new DAOException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            if (conn != null) {
                connPool.free(conn);
            }

        }
    }

    public List<VersionBean> getVersionsOfDocument(String login, long docNameCode) throws MyException {
        List<VersionBean> versionBeans = new ArrayList<VersionBean>();
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setAutoCommit(false);
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
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            throw new DAOException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            if (conn != null) {
                connPool.free(conn);
            }
        }


    }

    public VersionBean getVersion(String login, long docNameCode, long versName) throws MyException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setAutoCommit(false);
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
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            throw new DAOException(e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            if (conn != null) {
                connPool.free(conn);
            }
        }


    }


    public void deleteDocument(String login, long docNameCode) throws MyException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setAutoCommit(false);
            DocumentDAO docDAO = DAOFactory.getInstance().getDocumentDAO(conn);
            docDAO.deleteDocument(login, docNameCode);
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            throw new DAOException(e);

        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            if (conn != null) {
                connPool.free(conn);
            }
        }
    }

    public void deleteVersion(long versName, long docCode, String login) throws MyException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setAutoCommit(false);
            VersionDAO verDAO = DAOFactory.getInstance().getVersionDAO(conn);
            verDAO.deleteVersion(versName, docCode, login);
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            throw new DAOException(e);

        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            if (conn != null) {
                connPool.free(conn);
            }
        }

    }

    public long getDocumentIDByCodeNameAndLogin(String login, long docName) throws MyException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setAutoCommit(false);
            DocumentDAO docDAO = DAOFactory.getInstance().getDocumentDAO(conn);
            long id = docDAO.getDocumentID(login, docName);
            conn.commit();
            return id;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            throw new DAOException(e);

        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            if (conn != null) {
                connPool.free(conn);
            }
        }
    }

    public String getVersionType(long versionName, long documentName, String login) throws MyException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setAutoCommit(false);
            VersionDAO verDAO = DAOFactory.getInstance().getVersionDAO(conn);
            String type = verDAO.getVersionType(versionName, documentName, login);
            conn.commit();
            return type;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            throw new DAOException(e);

        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            if (conn != null) {
                connPool.free(conn);
            }
        }
    }

    @Override
    public void editVersionDescription(VersionBean versionBean) throws MyException, SystemException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setAutoCommit(false);
            VersionDAO verDAO = DAOFactory.getInstance().getVersionDAO(conn);
            String login = versionBean.getAuthor().getLogin();
            long codeDocName = versionBean.getDocument().getCodeDocumentName();
            long versionName = versionBean.getVersionName();
            String description = versionBean.getDescription();
            verDAO.updateVersionDescription(login, codeDocName, versionName, description);
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            throw new DAOException(e);

        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            if (conn != null) {
                connPool.free(conn);
            }
        }
    }

    @Override
    public void editDocumentDescription(DocumentBean documentBean) throws MyException, SystemException {
        Connection conn = null;
        ConnectionPool connPool = null;
        try {
            connPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            conn = connPool.getConnection();
            conn.setAutoCommit(false);
            DocumentDAO verDAO = DAOFactory.getInstance().getDocumentDAO(conn);
            String login = documentBean.getAuthor().getLogin();
            long codeDocName = documentBean.getCodeDocumentName();
            String description = documentBean.getDescription();
            verDAO.updateDocumentDescription(login, codeDocName, description);
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            throw new DAOException(e);

        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            if (conn != null) {
                connPool.free(conn);
            }
        }
    }
}