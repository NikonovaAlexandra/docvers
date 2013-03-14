package dao.author;

import entities.Author;
import entities.AuthorEntity;
import exception.DAOException;
import exception.SystemException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.util.List;

public class AuthorDAOImplH implements AuthorDAO{
    public List<AuthorEntity> getAuthor() {
        SessionFactory sessionFactory = new AnnotationConfiguration().configure(new File("C:\\Documents and Settings\\alni\\Desktop\\New Folder\\trunk\\DocsVersProject\\BakendModule\\src\\main\\java\\hibernate.xml")).buildSessionFactory();
        return sessionFactory.openSession().createCriteria(AuthorEntity.class).list();
    }

    public static void main(String[] args) {
        List<AuthorEntity> list = new AuthorDAOImplH().getAuthor();
        for (int i = 0; i < list.size(); i++) {
            AuthorEntity authorEntity =  list.get(i);
            System.out.println(authorEntity.getId() + " :: " + authorEntity.getLogin());
        }
    }

    @Override
    public Author getAuthorByID(long id) throws DAOException, SystemException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Author getAuthorByDocumentID(long id) throws DAOException, SystemException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Author getAuthorByLogin(String login) throws DAOException, SystemException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
