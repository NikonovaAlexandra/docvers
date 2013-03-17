package dao.version;

import entities.AuthorEntity;
import entities.DocumentEntity;
import entities.VersionEntity;
import exception.*;
import org.h2.constant.ErrorCode;
import org.h2.jdbc.JdbcBatchUpdateException;
import org.h2.jdbc.JdbcSQLException;
import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.exception.ExceptionUtils;
import service.QueriesHQL;

import java.io.File;
import java.lang.IllegalArgumentException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 14.03.13
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */
public class VersionDAOImplH {
    private Session session;

    public VersionDAOImplH(Session session) {
        this.session = session;

    }

    public static void main(String[] args) throws SystemException, DAOException {
        SessionFactory sessionFactory = new AnnotationConfiguration().configure(new File("BakendModule\\src\\main\\java\\hibernate.xml")).buildSessionFactory();
        Session session1 = sessionFactory.openSession();
        VersionDAOImplH dao = new VersionDAOImplH(session1);
//        DocumentEntity d = new DocumentEntity();
        AuthorEntity a = new AuthorEntity();
        a.setId(3);
        a.setLogin("author2");
        a.setPassword("pass2");
//        dao.deleteDocument("author2", 2218442);
//        d.setAuthorId(a);
//        d.setDocumentName("dsdsd");
//        dao.addDocument(d);
//        long d = dao.getDocumentID("author2", 2218442);
        DocumentEntity d = new DocumentEntity();
        d.setAuthorId(a);
        d.setCodeDocumentName(-1990522244);
        d.setId(35);
        d.setDocumentName("Ð°Ð¿Ð°Ð¿Ð¿Ð°Ð°Ð¿");
        VersionEntity v = new VersionEntity();
        v.setAuthorId(a);
        v.setDocumentId(d);
        dao.addVersion(v);
        //System.out.println(dao.getVersionType(1,-1990522244,"author2"));
        System.out.println(dao.getLastVersionNameInfo(35));
//        List<VersionEntity> li = dao.getVersionsOfDocument(35);
//        for (int i = 0; i < li.size(); ++i) {
//            System.out.println(li.get(i).getId());
//        }
        session1.close();

    }

    public List<VersionEntity> getVersionsOfDocument(long id) throws DAOException, SystemException {
        Transaction tr = session.beginTransaction();
        try {
            Query query = session.createQuery(QueriesHQL.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID);
            query.setLong("id", id);
            List<VersionEntity> versions = query.list();
            tr.commit();
            if (versions.isEmpty()) throw new NoSuchObjectInDB("Versions of this document");
            return versions;
        } catch (Exception e) {
            if (ExceptionUtils.getCause(e) instanceof JdbcSQLException) {
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                    throw new NullConnectionException(e);
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                    throw new NotEnoughRightsException(e);
                }
            }
            if (e instanceof SessionException) {
                throw new NullConnectionException(e);
            }
            throw new DAOException(e);

        }
    }

    public void addVersion(VersionEntity version) throws DAOException, SystemException {
        Transaction tr = null;
        if (version == null) {
            throw new IllegalArgumentException();
        }
        try {
            //todo exception???
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
            if (ExceptionUtils.getCause(e) instanceof JdbcSQLException || ExceptionUtils.getCause(e) instanceof JdbcBatchUpdateException) {
                e = (JdbcSQLException) ExceptionUtils.getCause(e);
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.DUPLICATE_KEY_1) {
                    throw new ObjectAlreadyExistsException();
                }
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                    throw new NullConnectionException(e);
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.REFERENTIAL_INTEGRITY_VIOLATED_PARENT_MISSING_1)
                    throw new ReferentialIntegrityViolatedException();
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                    throw new NotEnoughRightsException(e);
                }
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.NO_DISK_SPACE_AVAILABLE) {
                    throw new NoDiskSpaceException(e);
                }
            }
            if (e instanceof SessionException) {
                throw new NullConnectionException(e);
            }
            throw new DAOException(e);
        }

    }

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
                }
            }
            if (e instanceof SessionException) {
                throw new NullConnectionException(e);
            }
            throw new DAOException(e);
        }
    }

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
                }
            }
            if (e instanceof SessionException) {
                throw new NullConnectionException(e);
            }
            throw new DAOException(e);

        }
    }

    public VersionEntity getVersion(long id, long versName) throws DAOException, SystemException {
        VersionEntity version = null;
        Transaction tr = session.beginTransaction();
        try {
            Query query = session.createQuery(QueriesHQL.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID_AND_VERSION_NAME);
            query.setLong("id", id);
            query.setLong("versionName", versName);
            version = (VersionEntity) query.uniqueResult();
            tr.commit();
            if (version == null) throw new NoSuchObjectInDB("Version of this document with same name = " + versName);
            return version;
        } catch (Exception e) {
            if (ExceptionUtils.getCause(e) instanceof JdbcSQLException) {
                if (ErrorCode.CONNECTION_BROKEN_1 == ((JdbcSQLException) e).getErrorCode())
                    throw new NullConnectionException(e);
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                    throw new NotEnoughRightsException(e);
                }
            }
            if (e instanceof SessionException) {
                throw new NullConnectionException(e);
            }
            throw new DAOException(e);

        }
    }

    public long getLastVersionNameInfo(long docID) throws DAOException, SystemException {
        Transaction tr = session.beginTransaction();
        try {
            Query query = session.createQuery(QueriesHQL.SELECT_VERSION_NAME_FROM_VERSION);
            query.setLong("id", docID);
            Long versionName = (Long) query.uniqueResult();
            tr.commit();
            return versionName;
        } catch (Exception e) {
            if (ExceptionUtils.getCause(e) instanceof JdbcSQLException) {
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                    throw new NullConnectionException(e);
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                    throw new NotEnoughRightsException(e);
                }
            }
            if (e instanceof SessionException) {
                throw new NullConnectionException(e);
            }
            throw new DAOException(e);

        }
    }
}
