package dao.author;

import dao.ExceptionsThrower;
import entities.Author;
import exception.DAOException;
import exception.MyException;
import exception.SystemException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Restrictions;

import java.io.File;


/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 20.03.13
 * Time: 9:36
 * To change this template use File | Settings | File Templates.
 */
public class AuthorDAOImplHCriteria implements AuthorDAO {
    private Session session;

    public AuthorDAOImplHCriteria(Session session) {
        this.session = session;

    }

    @Override
    public Author getAuthorByID(long id) throws MyException {
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            Criteria criteria = session.createCriteria(Author.class);
            criteria.add(Restrictions.idEq(id));
            Author author = (Author) criteria.uniqueResult();
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
            Criteria criteria = session.createCriteria(Author.class);
            criteria.add(Restrictions.eq("login", login));
            Author author = (Author) criteria.uniqueResult();
            tr.commit();
            return author;
        } catch (Exception e) {
            if (tr != null && tr.isActive()) tr.rollback();
            throw ExceptionsThrower.throwException(e);
        }
    }

//    public static void main(String[] args) throws MyException {
//        SessionFactory sessionFactory = new AnnotationConfiguration().configure(new File("BakendModule\\src\\main\\java\\hibernate.xml")).buildSessionFactory();
//        Session session1 = sessionFactory.openSession();
//        AuthorDAOImplHCriteria dao = new AuthorDAOImplHCriteria(session1);
//        System.out.println(dao.getAuthorByLogin("author2").getId());
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
//        VersionEntity v = new VersionEntity();
//        v.setAuthorId(a);
//        v.setDocumentId(d);
//        dao.addVersion(v);
//        System.out.println(dao.getVersionType(1,-1990522244,"author2"));
//        System.out.println(dao.getDocumentsByAuthorID(3));
//        dao.addDocument(d);
//        List<Document> li = dao.getDocumentsByAuthorID(3);
//        for (int i = 0; i < li.size(); ++i) {
//            System.out.println(li.get(i).getId());
//        }
//        session1.close();
//
//    }
}
