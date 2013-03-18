package dao;

import dao.author.AuthorDAO;
import dao.author.AuthorDAOImpl;
import dao.author.AuthorDAOImplH;
import dao.document.DocumentDAO;
import dao.document.DocumentDAOImpl;
import dao.document.DocumentDAOImplH;
import dao.version.VersionDAO;
import dao.version.VersionDAOImpl;
import dao.version.VersionDAOImplH;
import exception.DAOException;
import exception.SystemException;
import org.hibernate.Session;

import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 12.02.13
 * Time: 9:04
 * To change this template use File | Settings | File Templates.
 */
public class DAOFactory {

    private static DocumentDAO documentDAO = null;
    private static AuthorDAO authorDAO = null;
    private static VersionDAO versionDAO = null;
    private static DAOFactory instance = null;

    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    public DocumentDAO getDocumentDAO(Connection conn) throws DAOException, SystemException {
        if (documentDAO == null) {
            documentDAO = new DocumentDAOImpl(conn);
        }
        return documentDAO;
    }

    public AuthorDAO getAuthorDAO(Connection conn) throws DAOException, SystemException {
        if (authorDAO == null) {
            authorDAO = new AuthorDAOImpl(conn);
        }
        return authorDAO;
    }

    public VersionDAO getVersionDAO(Connection conn) throws DAOException, SystemException {
        if (versionDAO == null) {
            versionDAO = new VersionDAOImpl(conn);
        }
        return versionDAO;
    }

    public DocumentDAO getDocumentDAO(Session session) throws DAOException, SystemException {
        if (documentDAO == null) {
            documentDAO = new DocumentDAOImplH(session);
        }
        return documentDAO;
    }

    public AuthorDAO getAuthorDAO(Session session) throws DAOException, SystemException {
        if (authorDAO == null) {
            authorDAO = new AuthorDAOImplH(session);
        }
        return authorDAO;
    }

    public VersionDAO getVersionDAO(Session session) throws DAOException, SystemException {
        if (versionDAO == null) {
            versionDAO = new VersionDAOImplH(session);
        }
        return versionDAO;
    }
}
