package dao.document;

import entities.Author;
import entities.Document;
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
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
public class DocumentDAOImplH implements DocumentDAO{

    private Session session;

    public DocumentDAOImplH(Session session) {
        this.session = session;

    }

//    public static void main(String[] args) throws SystemException, DAOException {
//        SessionFactory sessionFactory = new AnnotationConfiguration().configure(new File("BakendModule\\src\\main\\java\\hibernate.xml")).buildSessionFactory();
//        Session session1 = sessionFactory.openSession();
//        DocumentDAOImplH dao = new DocumentDAOImplH(session1);
////        DocumentEntity d = new DocumentEntity();
//        Author a = new Author();
//        a.setId(3);
//        a.setLogin("author2");
//        a.setPassword("pass2");
//
//        Document d = new Document();
//        d.setAuthorId(a);
//        d.setDocumentName("nananananan");
//        d.setCodeDocumentName(1212121212);
//        d.setId(35);
//
////        VersionEntity v = new VersionEntity();
////        v.setAuthorId(a);
////        v.setDocumentId(d);
////        dao.addVersion(v);
//        //System.out.println(dao.getVersionType(1,-1990522244,"author2"));
////        System.out.println(dao.getDocumentsByAuthorID(3));
//        //dao.addDocument(d);
//        List<Document> li = dao.getDocumentsByAuthorID(3);
//        for (int i = 0; i < li.size(); ++i) {
//            System.out.println(li.get(i).getId());
//        }
//        session1.close();
//
//    }
    @Override
    public Document getDocumentByAuthorAndName(String login, long docNameCode) throws DAOException, SystemException {

        Document doc = null;
        try {
            Transaction tr = session.beginTransaction();
            Query query = session.createQuery(QueriesHQL.SELECT_FROM_DOCUMENT_WHERE_DOCUMENT_NAME_CODE_AND_AUTHOR_ID);
            query.setString("login", login);
            query.setLong("codeDocumentName", docNameCode);
            doc = (Document) query.uniqueResult();
            tr.commit();
            if (doc == null)
                throw new NoSuchObjectInDB("There are no documents in database that matches your request.");
            return doc;
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
    public long getDocumentID(String login, long docName) throws DAOException, SystemException {
        Transaction tr = session.beginTransaction();

        try {
            Query query = session.createQuery(QueriesHQL.SELECT_ID_FROM_DOCUMENT);
            query.setString("login", login);
            query.setLong("codeDocumentName", docName);
            Long id = new Long(0);
            id = (Long) query.uniqueResult();
            tr.commit();
            if (id.equals(0)) throw new NoSuchObjectInDB("There are no documents in database that matches your request.");
            return id;
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
    public void addDocument(Document document) throws DAOException, SystemException {
        Transaction tr = session.beginTransaction();
        if (document == null) {
            throw new IllegalArgumentException();
        }
        if (document.getDocumentName().length() > 20) {
            throw new exception.IllegalArgumentException("Too long name");
        }
        try {
            session.save(document);
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
                if (((JdbcBatchUpdateException) e).getErrorCode() == ErrorCode.REFERENTIAL_INTEGRITY_VIOLATED_PARENT_MISSING_1) {
                    throw new ReferentialIntegrityViolatedException();
                } else throw new DAOException(e);

            }
            if (e instanceof SessionException) {
                throw new NullConnectionException(e);
            }
            throw new DAOException(e);

        }

    }

    @Override
    public List<Document> getDocumentsByAuthorID(long id) throws DAOException, SystemException {
        Transaction tr = session.beginTransaction();
        try {
            Query query = session.createQuery(QueriesHQL.SELECT_FROM_DOCUMENT_WHERE_AUTHOR_ID);
            query.setLong("docId", id);
            List<Document> docs = query.list();
            tr.commit();
            if (docs.isEmpty()) throw new NoSuchObjectInDB("There are no documents in database that matches your request.");
            return docs;
        } catch (Exception e) {
            if (ExceptionUtils.getCause(e) instanceof JdbcSQLException) {
                e = (JdbcSQLException) ExceptionUtils.getCause(e);
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                    throw new NullConnectionException(e);
                if (((JdbcSQLException) e).getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                    throw new NotEnoughRightsException(e);
                } else {
                    throw new DAOException(e);
                }
            }
            if (e instanceof SessionException) {
                throw new NullConnectionException(e);
            }
            throw new DAOException(e);

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
        Transaction tr = session.beginTransaction();
        try {
            Query query = session.createQuery(QueriesHQL.DELETE_FROM_DOCUMENT_WHERE_AUTHOR_ID_AND_CODE);
            query.setString("login", login);
            query.setLong("codeDocumentName", docNameCode);
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
                }  else throw new DAOException(e);
            }
            if (e instanceof SessionException) {
                throw new NullConnectionException(e);
            }
            throw new DAOException(e);
        }
    }

}
