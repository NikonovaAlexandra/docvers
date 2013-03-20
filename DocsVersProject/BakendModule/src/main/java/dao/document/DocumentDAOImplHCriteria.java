package dao.document;

import dao.ExceptionsThrower;
import entities.Document;
import exception.DAOException;
import exception.NoSuchObjectInDB;
import exception.SystemException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 20.03.13
 * Time: 9:35
 * To change this template use File | Settings | File Templates.
 */
public class DocumentDAOImplHCriteria implements DocumentDAO {

    private Session session;

    public DocumentDAOImplHCriteria(Session session) {
        this.session = session;

    }

    @Override
    public Document getDocumentByAuthorAndName(String login, long docNameCode) throws DAOException, SystemException {
        Transaction tr = null;
        Document doc = null;
        try {
            tr = session.beginTransaction();
            Criteria criteria = session.createCriteria(Document.class);
            criteria.add(Restrictions.eq("authorId.login", login));
            criteria.add(Restrictions.eq("codeDocumentName", docNameCode));
            doc = (Document) criteria.uniqueResult();
            tr.commit();
            if (doc == null)
                throw new NoSuchObjectInDB("There are no documents in database that matches your request.");
            return doc;
        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            ExceptionsThrower.throwException(e);
            return null;

        }
    }

    @Override
    public long getDocumentID(String login, long docName) throws DAOException, SystemException {
        Transaction tr = null;

        try {
            tr = session.beginTransaction();
            Criteria criteria = session.createCriteria(Document.class);
            criteria.setProjection(Projections.id());
            criteria.add(Restrictions.eq("authorId.login", login));
            criteria.add(Restrictions.eq("codeDocumentName", docName));
            Long id = new Long(0);
            id = (Long) criteria.uniqueResult();
            tr.commit();
            if (id.equals(0))
                throw new NoSuchObjectInDB("There are no documents in database that matches your request.");
            return id;
        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            ExceptionsThrower.throwException(e);
            return 0;
        }
    }

    @Override
    public void addDocument(Document document) throws DAOException, SystemException {
        Transaction tr = null;
        if (document == null) {
            throw new IllegalArgumentException();
        }
        if (document.getDocumentName().length() > 20) {
            throw new exception.IllegalArgumentException("Too long name");
        }
        try {
            tr = session.beginTransaction();
            session.save(document);
            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            ExceptionsThrower.throwException(e);
        }

    }

    @Override
    public List<Document> getDocumentsByAuthorID(long id) throws DAOException, SystemException {
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Criteria criteria = session.createCriteria(Document.class);
            criteria.add(Restrictions.eq("authorId.id", id));
            criteria.addOrder(Order.desc("id"));
            List<Document> docs = criteria.list();
            tr.commit();
            if (docs.isEmpty())
                throw new NoSuchObjectInDB("There are no documents in database that matches your request.");
            return docs;
        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            ExceptionsThrower.throwException(e);
            return null;

        }
    }

    @Override
    public void editDocumentDescription(String login, String docName, String newDescription) throws DAOException, SystemException {

//        PreparedStatement ps = null;
//        try {
//            ps = conn.prepareStatement(QueriesSQL.UPDATE_DOCUMENT_SET_DESCRIPTION_WHERE_DOCUMENT_NAME_AND_LOGIN);
//            ps.setString(1, newDescription);
//            ps.setString(2, docName);
//            ps.setString(3, login);
//            conn.commit();
//        } catch (SQLException e) {
//            if (e.getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
//                throw new NullConnectionException(e);
//            if (e.getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
//                throw new NotEnoughRightsException("", e);
//            } else throw new DAOException(e);
//
//        } finally {
//            try {
//                if (ps != null) ps.close();
//            } catch (SQLException e) {
//                throw new DAOException(e);
//            }
//        }
    }

    @Override
    public void deleteDocument(String login, long docNameCode) throws DAOException, SystemException {
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Criteria criteria = session.createCriteria(Document.class);
            criteria.add(Restrictions.eq("authorId.login", login));
            criteria.add(Restrictions.eq("codeDocumentName", docNameCode));
            Document doc = (Document) criteria.uniqueResult();
            if (doc == null) {
                throw new NoSuchObjectInDB("Nothing to delete");
            } else {
                session.delete(doc);
                tr.commit();
            }

        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            ExceptionsThrower.throwException(e);
        }
    }
}
