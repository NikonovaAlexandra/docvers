package dao.version;

import dao.ExceptionsThrower;
import entities.Version;
import exception.*;
import org.h2.constant.ErrorCode;
import org.h2.jdbc.JdbcBatchUpdateException;
import org.h2.jdbc.JdbcSQLException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;
import org.hibernate.exception.ExceptionUtils;
import service.QueriesHQL;

import java.lang.IllegalArgumentException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 14.03.13
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */
public class VersionDAOImplHHQL implements VersionDAO {
    private Session session;

    public VersionDAOImplHHQL(Session session) {
        this.session = session;

    }

    @Override
    public List<Version> getVersionsOfDocument(long id) throws DAOException, SystemException {
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Query query = session.createQuery(QueriesHQL.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID);
            query.setLong("id", id);
            List<Version> versions = query.list();
            tr.commit();
            if (versions.isEmpty()) throw new NoSuchObjectInDB("Versions of this document");
            return versions;
        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            ExceptionsThrower.throwException(e);
            return null;
        }
    }

    @Override
    public void addVersion(Version version) throws DAOException, SystemException {
        Transaction tr = null;
        if (version == null) {
            throw new IllegalArgumentException();
        }
        try {
            tr = session.beginTransaction();
            Query query = session.createQuery(QueriesHQL.UPDATE_VERSION_SET_IS_RELEASED);
            query.setBoolean("isReleased", true);
            query.setLong("id", version.getDocumentId().getId());
            query.executeUpdate();
            session.cancelQuery();
            long name = getLastVersionNameInfo(version.getDocumentId().getId()) + 1;
            version.setVersionName(name);
            version.setReleased(false);
            session.save(version);

            tr.commit();


        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            ExceptionsThrower.throwException(e);
        }

    }

    @Override
    public void deleteVersion(long versName, long docCode, String login) throws DAOException, SystemException {
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Query query = session.createQuery(QueriesHQL.DELETE_FROM_VERSION_WHERE_VERSION_NAME_AND_DOC_AND_LOGIN);
            query.setString("login", login);
            query.setLong("codeDocumentName", docCode);
            query.setLong("versionName", versName);
            int i = query.executeUpdate();
            if (i == 0) throw new NoSuchObjectInDB("Nothing to delete");
            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public String getVersionType(long versionName, long documentName, String login) throws DAOException, SystemException {
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Query query = session.createQuery(QueriesHQL.SELECT_VERSION_TYPE_FROM_VERSION);
            query.setString("login", login);
            query.setLong("codeDocumentName", documentName);
            query.setLong("versionName", versionName);
            String type = (String) query.uniqueResult();
            tr.commit();
            if (type.isEmpty()) throw new NoSuchObjectInDB("Versions of this document");
            return type;
        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            ExceptionsThrower.throwException(e);
            return null;
        }
    }

    @Override
    public Version getVersion(long id, long versName) throws DAOException, SystemException {
        Version version = null;
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Query query = session.createQuery(QueriesHQL.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID_AND_VERSION_NAME);
            query.setLong("id", id);
            query.setLong("versionName", versName);
            version = (Version) query.uniqueResult();
            tr.commit();
            if (version == null) throw new NoSuchObjectInDB("Version of this document with same name = " + versName);
            return version;
        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            ExceptionsThrower.throwException(e);
            return null;
        }
    }

    @Override
    public long getLastVersionNameInfo(long docID) throws DAOException, SystemException {
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Query query = session.createQuery(QueriesHQL.SELECT_VERSION_NAME_FROM_VERSION);
            query.setLong("id", docID);
            Long versionName = (Long) query.uniqueResult();
            tr.commit();
            return versionName;
        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            ExceptionsThrower.throwException(e);
            return 0;
        }
    }
}
