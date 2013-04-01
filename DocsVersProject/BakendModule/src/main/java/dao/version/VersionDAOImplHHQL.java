package dao.version;

import dao.ExceptionsThrower;
import entities.Version;
import exception.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ExceptionUtils;
import service.QueriesHQL;

import javax.swing.*;
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
    public List<Version> getVersionsOfDocument(long id) throws MyException {
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
            throw ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public void addVersion(Version version) throws MyException{
        Transaction tr = null;
        if (version == null) {
            throw new IllegalArgumentException();
        }
        try {
            //long name = getLastVersionNameInfo(version.getDocumentID());
            tr = session.beginTransaction();
            Query query = session.createQuery(QueriesHQL.UPDATE_VERSION_SET_IS_RELEASED);
            query.setBoolean("isReleased", true);
            query.setLong("id", version.getDocumentId().getId());
            query.executeUpdate();
            session.cancelQuery();
            session.clear();
            //version.setVersionName(name + 1);
            version.setReleased(false);
            session.save(version);

            tr.commit();


        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            throw ExceptionsThrower.throwException(e);
        }

    }

    @Override
    public void deleteVersion(long versName, long docCode, String login) throws MyException{
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
            throw ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public String getVersionType(long versionName, long documentName, String login) throws MyException{
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
            throw ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public Version getVersion(long id, long versName) throws MyException{
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
            throw ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public void updateVersionDescription(String login, long codeDocName, long versionName, String description) throws MyException {
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Query query = session.createQuery(QueriesHQL.UPDATE_VERSION_DESCRIPTION);
            query.setString("login", login);
            query.setString("description", description);
            query.setLong("codeDocumentName", codeDocName);
            query.setLong("versionName", versionName);
            query.executeUpdate();
            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            throw ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public long getLastVersionNameInfo(long docID) throws MyException{
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Query query = session.createQuery(QueriesHQL.SELECT_VERSION_NAME_FROM_VERSION);
            query.setLong("id", docID);
            Long l = (Long) query.uniqueResult();
            long versionName = (l == null ? 0 : l);
            tr.commit();
            return versionName;
        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            throw ExceptionsThrower.throwException(e);
        }
    }
}
