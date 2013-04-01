package dao.version;

import dao.ExceptionsThrower;
import entities.Document;
import entities.Version;
import exception.DAOException;
import exception.MyException;
import exception.NoSuchObjectInDB;
import exception.SystemException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import service.QueriesHQL;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 20.03.13
 * Time: 9:35
 * To change this template use File | Settings | File Templates.
 */
public class VersionDAOImplHCriteria implements VersionDAO{
    private Session session;

    public VersionDAOImplHCriteria(Session session) {
        this.session = session;

    }

    @Override
    public List<Version> getVersionsOfDocument(long id) throws MyException {

        try {
            Criteria criteria = session.createCriteria(Version.class);
            criteria.createAlias("documentId", "document").add(Restrictions.eq("document.id", id));
            criteria.addOrder(Order.desc("versionName"));
            List<Version> versions = criteria.list();
            if (versions.isEmpty()) throw new NoSuchObjectInDB("Versions of this document");
            return versions;
        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public void addVersion(Version version) throws MyException {
        if (version == null) {
            throw new IllegalArgumentException();
        }
        try {
            Criteria criteria = session.createCriteria(Version.class);
            criteria.add(Restrictions.eq("released", false));
            criteria.createAlias("documentId", "document").add(Restrictions.eq("document.id", version.getDocumentId().getId()));
            List<Version> versions = criteria.list();
            for (Version v: versions) {
                v.setReleased(true);
                session.update(v);
            }
            version.setReleased(false);
            session.save(version);

        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }

    }

    @Override
    public void deleteVersion(long versName, long docCode, String login) throws MyException {
        try {
            Criteria criteria = session.createCriteria(Version.class);
            criteria.createAlias("documentId", "document").add(Restrictions.eq("document.codeDocumentName", docCode)).createAlias("authorId", "author").add(Restrictions.eq("author.login", login));
//            criteria.createAlias("documentId", "document").add(Restrictions.eq("document.codeDocumentName", docCode));
            criteria.add(Restrictions.eq("versionName", versName));
            Version version = (Version) criteria.uniqueResult();
            if (version == null) {
                throw new NoSuchObjectInDB("Nothing to delete");
            } else {
                session.delete(version);
            }
        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public String getVersionType(long versionName, long documentName, String login) throws MyException {
        try {
            Criteria criteria = session.createCriteria(Version.class);
            criteria.createAlias("documentId", "document").add(Restrictions.eq("document.codeDocumentName", documentName)).createAlias("authorId", "author").add(Restrictions.eq("author.login", login));
//            criteria.createAlias("documentId", "document").add(Restrictions.eq("document.codeDocumentName", documentName));
            criteria.add(Restrictions.eq("versionName", versionName));
            criteria.setProjection(Projections.property("versionType"));
            String type = (String) criteria.uniqueResult();
            if (type.isEmpty()) throw new NoSuchObjectInDB("Versions of this document");
            return type;
        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public Version getVersion(long id, long versName) throws MyException {
        Version version = null;
        try {
            Criteria criteria = session.createCriteria(Version.class);
            criteria.createAlias("documentId", "document").add(Restrictions.eq("document.id", id));
            criteria.add(Restrictions.eq("versionName", versName));
            version = (Version) criteria.uniqueResult();
            if (version == null) throw new NoSuchObjectInDB("Version of this document with same name = " + versName);
            return version;
        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public void updateVersionDescription(String login, long codeDocName, long versionName, String description) throws MyException {
        try {
            Criteria criteria = session.createCriteria(Version.class);
            criteria.createAlias("documentId", "document").add(Restrictions.eq("document.codeDocumentName", codeDocName)).createAlias("authorId", "author").add(Restrictions.eq("author.login", login));
            criteria.add(Restrictions.eq("versionName", versionName));
            Version vers = (Version) criteria.uniqueResult();
            vers.setVersionDescription(description);
            session.flush();
        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public long getLastVersionNameInfo(long docID) throws MyException {
        try {
            Criteria criteria = session.createCriteria(Version.class);
            criteria.createAlias("documentId", "document").add(Restrictions.eq("document.id", docID));
            criteria.setProjection(Projections.max("versionName"));
            Long l = (Long) criteria.uniqueResult();
            long versionName = (l == null ? 0 : l);
            return versionName;
        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }
    }
}
