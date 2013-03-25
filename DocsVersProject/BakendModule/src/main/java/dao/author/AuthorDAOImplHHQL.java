package dao.author;

import dao.ExceptionsThrower;
import entities.Author;
import exception.DAOException;
import exception.MyException;
import exception.NoSuchObjectInDB;
import exception.SystemException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.QueriesHQL;

public class AuthorDAOImplHHQL implements AuthorDAO {
    private Session session;

    public AuthorDAOImplHHQL(Session session) {
        this.session = session;

    }
//
//    public static void main(String[] args) throws SystemException, DAOException {
//        SessionFactory sessionFactory = new AnnotationConfiguration().configure(new File("BakendModule\\src\\main\\java\\hibernate.xml")).buildSessionFactory();
//        Session session1 = sessionFactory.openSession();
//        AuthorDAOImplHHQL dao = new AuthorDAOImplHHQL(session1);
//
//        Author a = dao.getAuthorByLogin("author2");
//        System.out.println(a.getLogin());
//        session1.close();
//
//    }

    @Override
    public Author getAuthorByID(long id) throws MyException {
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Query query = session.createQuery(QueriesHQL.SELECT_FROM_AUTHOR_WHERE_ID);
            query.setLong("id", id);
            Author author = (Author) query.uniqueResult();
            tr.commit();
            return author;
        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            throw ExceptionsThrower.throwException(e);

        }
    }

    @Override
    public Author getAuthorByLogin(String login) throws MyException {
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Query query = session.createQuery(QueriesHQL.SELECT_FROM_AUTHOR_WHERE_LOGIN);
            query.setString("login", login);
            Author author = (Author) query.uniqueResult();
            tr.commit();
            return author;
        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            throw ExceptionsThrower.throwException(e);

        }
    }
}
