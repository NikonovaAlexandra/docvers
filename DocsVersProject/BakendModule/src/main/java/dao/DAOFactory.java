package dao;

import dao.author.AuthorDAO;
import dao.author.AuthorDAOImpl;
import dao.author.AuthorDAOImplHCriteria;
import dao.author.AuthorDAOImplHHQL;
import dao.document.DocumentDAO;
import dao.document.DocumentDAOImpl;
import dao.document.DocumentDAOImplHCriteria;
import dao.document.DocumentDAOImplHHQL;
import dao.version.VersionDAO;
import dao.version.VersionDAOImpl;
import dao.version.VersionDAOImplHCriteria;
import dao.version.VersionDAOImplHHQL;
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
    public static DAOFactory getInstance() {
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

    public DocumentDAO getDocumentDAO(Session session, DAOType type) throws DAOException, SystemException {
        if (documentDAO == null) {
            switch (type) {
                case CRITERIA: documentDAO = new DocumentDAOImplHCriteria(session); break;
                case HQL: documentDAO = new DocumentDAOImplHHQL(session); break;
                default: break;
            }
        }
        return documentDAO;
    }

    public AuthorDAO getAuthorDAO(Session session, DAOType type) throws DAOException, SystemException {
        if (authorDAO == null) {
            switch (type) {
                case CRITERIA: authorDAO = new AuthorDAOImplHCriteria(session); break;
                case HQL: authorDAO = new AuthorDAOImplHHQL(session); break;
                default: break;
            };
        }
        return authorDAO;
    }

    public VersionDAO getVersionDAO(Session session, DAOType type) throws DAOException, SystemException {
        if (versionDAO == null) {
            switch (type) {
                case CRITERIA: versionDAO = new VersionDAOImplHCriteria(session); break;
                case HQL: versionDAO = new VersionDAOImplHHQL(session); break;
                default: break;
            };
        }
        return versionDAO;
    }
}
