package dao.author;

import entities.Author;
import exception.DAOException;
import exception.NotEnoughRightsException;
import exception.NullConnectionException;
import exception.SystemException;
import org.h2.constant.ErrorCode;
import org.h2.jdbc.JdbcSQLException;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;
import org.hibernate.exception.ExceptionUtils;
import service.QueriesHQL;

public class AuthorDAOImplH implements AuthorDAO{
    private Session session;

    public AuthorDAOImplH(Session session) {
        this.session = session;

    }
//
//    public static void main(String[] args) throws SystemException, DAOException {
//        SessionFactory sessionFactory = new AnnotationConfiguration().configure(new File("BakendModule\\src\\main\\java\\hibernate.xml")).buildSessionFactory();
//        Session session1 = sessionFactory.openSession();
//        AuthorDAOImplH dao = new AuthorDAOImplH(session1);
//
//        Author a = dao.getAuthorByLogin("author2");
//        System.out.println(a.getLogin());
//        session1.close();
//
//    }

    @Override
    public Author getAuthorByID(long id) throws DAOException, SystemException {
        try {
            Transaction tr = session.beginTransaction();
            Author author = (Author) session.createQuery(QueriesHQL.SELECT_FROM_AUTHOR_WHERE_ID).setLong("id", id).uniqueResult();
            tr.commit();
            return author;
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
            } else throw new DAOException(e);

        }
    }

    @Override
    public Author getAuthorByLogin(String login) throws DAOException, SystemException {
        try {
            Transaction tr = session.beginTransaction();
            Author author = (Author) session.createQuery(QueriesHQL.SELECT_FROM_AUTHOR_WHERE_LOGIN).setString("login", login).uniqueResult();
            tr.commit();
            return author;
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
            } else throw new DAOException(e);

        }
    }
}
