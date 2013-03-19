package dao.version;

import entities.Version;
import exception.*;
import org.h2.constant.ErrorCode;
import org.h2.jdbc.JdbcBatchUpdateException;
import org.h2.jdbc.JdbcSQLException;
import org.hibernate.*;
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
public class VersionDAOImplH implements VersionDAO{
    private Session session;

    public VersionDAOImplH(Session session) {
        this.session = session;

    }

    @Override
    public List<Version> getVersionsOfDocument(long id) throws DAOException, SystemException {
        Transaction tr = session.beginTransaction();
        try {
            Query query = session.createQuery(QueriesHQL.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID);
            query.setLong("id", id);
            List<Version> versions = query.list();
            tr.commit();
            if (versions.isEmpty()) throw new NoSuchObjectInDB("Versions of this document");
            return versions;
        } catch (Exception e) {
            if (ExceptionUtils.getCause(e) instanceof JdbcSQLException) {
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                    throw new NullConnectionException(e);
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                    throw new NotEnoughRightsException(e);
                } else throw new DAOException(e);
            }
            if (e instanceof SessionException) {
                throw new NullConnectionException(e);
            }
            throw new DAOException(e);

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
            tr.rollback();
            if (ExceptionUtils.getCause(e) instanceof JdbcSQLException) {
                e = (JdbcSQLException) ExceptionUtils.getCause(e);
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                    throw new NullConnectionException(e);

                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                    throw new NotEnoughRightsException(e);
                }
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.NO_DISK_SPACE_AVAILABLE) {
                    throw new NoDiskSpaceException(e);
                } else throw new DAOException(e);
            }
            if (ExceptionUtils.getCause(e) instanceof JdbcBatchUpdateException){
                e = (JdbcBatchUpdateException) ExceptionUtils.getCause(e);
                if (((JdbcBatchUpdateException) e).getErrorCode() == ErrorCode.DUPLICATE_KEY_1) {
                    throw new ObjectAlreadyExistsException();
                }
                if (((JdbcBatchUpdateException) e).getErrorCode() == ErrorCode.NULL_NOT_ALLOWED) {
                    throw new IntegrityConstraintException(e, "NULL is not allowed");
                }
                if (((JdbcBatchUpdateException) e).getErrorCode() == ErrorCode.REFERENTIAL_INTEGRITY_VIOLATED_PARENT_MISSING_1)
                    throw new ReferentialIntegrityViolatedException();
                else throw new DAOException(e);

            }
            if (e instanceof SessionException) {
                throw new NullConnectionException(e);
            }
            throw new DAOException(e);
        }

    }

    @Override
    public void deleteVersion(long versName, long docCode, String login) throws DAOException, SystemException {
        Transaction tr = session.beginTransaction();
        try {
            Query query = session.createQuery(QueriesHQL.DELETE_FROM_VERSION_WHERE_VERSION_NAME_AND_DOC_AND_LOGIN);
            query.setString("login", login);
            query.setLong("codeDocumentName", docCode);
            query.setLong("versionName", versName);
            int i = query.executeUpdate();
            if (i == 0) throw new NoSuchObjectInDB("Nothing to delete");
            tr.commit();
        } catch (Exception e) {
            if (ExceptionUtils.getCause(e) instanceof JdbcSQLException) {
                e = (JdbcSQLException) ExceptionUtils.getCause(e);
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                    throw new NullConnectionException(e);
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                    throw new NotEnoughRightsException(e);
                } else throw new DAOException(e);
            }
            if (e instanceof SessionException) {
                throw new NullConnectionException(e);
            }
            throw new DAOException(e);
        }
    }

    @Override
    public String getVersionType(long versionName, long documentName, String login) throws DAOException, SystemException {
        Transaction tr = session.beginTransaction();
        try {
            Query query = session.createQuery(QueriesHQL.SELECT_VERSION_TYPE_FROM_VERSION);
            query.setString("login", login);
            query.setLong("codeDocumentName", documentName);
            query.setLong("versionName", versionName);
            String type = (String) query.uniqueResult();
            tr.commit();
            if (type.isEmpty()) throw new NoSuchObjectInDB("Versions of this document");
            return type;
        } catch (Exception e) {
            if (ExceptionUtils.getCause(e) instanceof JdbcSQLException) {
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                    throw new NullConnectionException(e);
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                    throw new NotEnoughRightsException(e);
                } else throw new DAOException(e);
            }
            if (e instanceof SessionException) {
                throw new NullConnectionException(e);
            }
            throw new DAOException(e);

        }
    }

    @Override
    public Version getVersion(long id, long versName) throws DAOException, SystemException {
        Version version = null;
        Transaction tr = session.beginTransaction();
        try {
            Query query = session.createQuery(QueriesHQL.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID_AND_VERSION_NAME);
            query.setLong("id", id);
            query.setLong("versionName", versName);
            version = (Version) query.uniqueResult();
            tr.commit();
            if (version == null) throw new NoSuchObjectInDB("Version of this document with same name = " + versName);
            return version;
        } catch (Exception e) {
            if (ExceptionUtils.getCause(e) instanceof JdbcSQLException) {
                if (ErrorCode.CONNECTION_BROKEN_1 == ((JdbcSQLException) e).getErrorCode())
                    throw new NullConnectionException(e);
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                    throw new NotEnoughRightsException(e);
                } else throw new DAOException(e);
            }
            if (e instanceof SessionException) {
                throw new NullConnectionException(e);
            }
            throw new DAOException(e);

        }
    }

    @Override
    public long getLastVersionNameInfo(long docID) throws DAOException, SystemException {
        try {
            Query query = session.createQuery(QueriesHQL.SELECT_VERSION_NAME_FROM_VERSION);
            query.setLong("id", docID);
            Long versionName = (Long) query.uniqueResult();
            return versionName;
        } catch (Exception e) {
            if (ExceptionUtils.getCause(e) instanceof JdbcSQLException) {
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                    throw new NullConnectionException(e);
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                    throw new NotEnoughRightsException(e);
                } else throw new DAOException(e);
            }
            if (e instanceof SessionException) {
                throw new NullConnectionException(e);
            }
            throw new DAOException(e);

        }
    }
}
