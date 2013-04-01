package dao.document;

import dao.ExceptionsThrower;
import entities.Document;
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
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
public class DocumentDAOImplHHQL implements DocumentDAO {

    private Session session;

    public DocumentDAOImplHHQL(Session session) {
        this.session = session;

    }

    @Override
    public Document getDocumentByAuthorAndName(String login, long docNameCode) throws MyException {
        Document doc = null;
        try {
            Query query = session.createQuery(QueriesHQL.SELECT_FROM_DOCUMENT_WHERE_DOCUMENT_NAME_CODE_AND_AUTHOR_ID);
            query.setString("login", login);
            query.setLong("codeDocumentName", docNameCode);
            doc = (Document) query.uniqueResult();
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
            Query query = session.createQuery(QueriesHQL.SELECT_ID_FROM_DOCUMENT);
            query.setString("login", login);
            query.setLong("codeDocumentName", docName);
            Long id = new Long(0);
            id = (Long) query.uniqueResult();
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
            Query query = session.createQuery(QueriesHQL.UPDATE_DOCUMENT_DESCRIPTION);
            query.setString("login", login);
            query.setString("description", description);
            query.setLong("codeDocumentName", codeDocName);
            query.executeUpdate();
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
            Query query = session.createQuery(QueriesHQL.SELECT_FROM_DOCUMENT_WHERE_AUTHOR_ID);
            query.setLong("docId", id);
            List<Document> docs = query.list();
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
            Query query = session.createQuery(QueriesHQL.DELETE_FROM_DOCUMENT_WHERE_AUTHOR_ID_AND_CODE);
            query.setString("login", login);
            query.setLong("codeDocumentName", docNameCode);
            int i = query.executeUpdate();
            if (i == 0) throw new NoSuchObjectInDB("Nothing to delete");
        } catch (Exception e) {
            throw ExceptionsThrower.throwException(e);
        }
    }

}
