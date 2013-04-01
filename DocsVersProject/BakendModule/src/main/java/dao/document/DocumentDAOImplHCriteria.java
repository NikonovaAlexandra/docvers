package dao.document;

import dao.ExceptionsThrower;
import entities.Document;
import entities.Version;
import exception.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.io.File;
import java.lang.IllegalArgumentException;
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
    public Document getDocumentByAuthorAndName(String login, long docNameCode) throws MyException {
        Document doc = null;
        try {
            Criteria criteria = session.createCriteria(Document.class);
            criteria.createAlias("authorId", "author").add(Restrictions.eq("author.login", login));
            criteria.add(Restrictions.eq("codeDocumentName", docNameCode));
            doc = (Document) criteria.uniqueResult();
            if (doc == null)
                throw new NoSuchObjectInDB("There are no documents in database that matches your request.");
            return doc;
        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);

        }
    }

    @Override
    public long getDocumentID(String login, long docName) throws MyException {

        try {
            Criteria criteria = session.createCriteria(Document.class);
            criteria.setProjection(Projections.id());
            criteria.createAlias("authorId", "author").add(Restrictions.eq("author.login", login));
            criteria.add(Restrictions.eq("codeDocumentName", docName));
            Long id = new Long(0);
            id = (Long) criteria.uniqueResult();
            if (id.equals(0))
                throw new NoSuchObjectInDB("There are no documents in database that matches your request.");
            return id;
        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public void updateDocumentDescription(String login, long codeDocName, String description) throws MyException {
        try {
            Criteria criteria = session.createCriteria(Document.class);
            criteria.add(Restrictions.eq("codeDocumentName", codeDocName)).createAlias("authorId", "author").add(Restrictions.eq("author.login", login));
            Document doc = (Document) criteria.uniqueResult();
            doc.setDescription(description);
            session.flush();
        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }
    }

    @Override
    public void addDocument(Document document) throws MyException {
        if (document == null) {
            throw new IllegalArgumentException();
        }
        if (document.getDocumentName().length() > 20) {
            throw new exception.IllegalArgumentException("Too long name");
        }
        try {
            session.save(document);
        } catch (Exception e) {
            session.clear();
            throw ExceptionsThrower.throwException(e);
        }

    }

    @Override
    public List<Document> getDocumentsByAuthorID(long id) throws MyException {
        try {
            Criteria criteria = session.createCriteria(Document.class);
            criteria.createAlias("authorId", "author").add(Restrictions.eq("author.id", id));
            criteria.addOrder(Order.desc("id"));
            List<Document> docs = criteria.list();
            if (docs.isEmpty())
                throw new NoSuchObjectInDB("There are no documents in database that matches your request.");
            return docs;
        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);

        }
    }


    @Override
    public void deleteDocument(String login, long docNameCode) throws MyException {

        try {
            Criteria criteria = session.createCriteria(Document.class);
            criteria.createAlias("authorId", "author").add(Restrictions.eq("author.login", login));
            criteria.add(Restrictions.eq("codeDocumentName", docNameCode));
            Document doc = (Document) criteria.uniqueResult();
            if (doc == null) {
                throw new NoSuchObjectInDB("Nothing to delete");
            } else {
                session.delete(doc);
            }

        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }
    }
}
