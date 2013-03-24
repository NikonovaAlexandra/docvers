package service;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 18.03.13
 * Time: 11:14
 * To change this template use File | Settings | File Templates.
 */
public class SessionFactoryUtil {
    private static SessionFactoryUtil instance;
    private SessionFactory sessionFactory;

    private SessionFactoryUtil(String path) {
        sessionFactory = new AnnotationConfiguration().configure(new File(path)).buildSessionFactory();
    }

    public static void init(String path) {
        if (instance == null) {
            instance = new SessionFactoryUtil(path);
        }
    }

    public static SessionFactoryUtil getInstance() {
        return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
