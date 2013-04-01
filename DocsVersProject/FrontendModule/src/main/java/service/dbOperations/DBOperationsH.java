package service.dbOperations;

import beans.AuthorBean;
import beans.Converter;
import beans.DocumentBean;
import beans.VersionBean;
import dao.DAOFactory;
import dao.DAOType;
import dao.author.AuthorDAO;
import dao.document.DocumentDAO;
import dao.version.VersionDAO;
import entities.Author;
import entities.Document;
import entities.Version;
import exception.MyException;
import exception.NoSuchObjectInDB;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import db.SessionFactoryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 18.03.13
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */
public class DBOperationsH implements DBOperations {
    private SessionFactory sessionFactory = SessionFactoryUtil.getInstance().getSessionFactory();
    private DAOType type;

    public DBOperationsH(DAOType type) {
        this.type = type;
    }

    @Override
    public void addDocument(DocumentBean documentBean) throws MyException {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            DocumentDAO documentDAO = DAOFactory.getInstance().getDocumentDAO(session, type);
            documentDAO.addDocument(Converter.convertDocumentBeanToDocumentH(documentBean));
            tr.commit();
        } catch (IllegalArgumentException e){
            tr.rollback();
            throw e;
        } catch (Exception e) {
            tr.rollback();
            if (e instanceof MyException) throw  (MyException)e;
            else throw new MyException(e);
        } finally {
            if (session != null) {
                // session.flush();
                session.clear();
                //session.close();
            }
        }
    }

    @Override
    public long getLastVersionNameInfo(long docID) throws MyException {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            VersionDAO versionDAO = DAOFactory.getInstance().getVersionDAO(session, type);
            long id = versionDAO.getLastVersionNameInfo(docID);
            tr.commit();
            return id;
        } catch (Exception e) {
            tr.rollback();
            if (e instanceof MyException) throw  (MyException)e;
            else throw new MyException(e);
        } finally {
            if (session != null) {
                //session.flush();
                session.clear();
                //session.close();
            }
        }
    }

    @Override
    public void addVersion(VersionBean versionBean) throws MyException {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            VersionDAO versionDAO = DAOFactory.getInstance().getVersionDAO(session, type);
            versionDAO.addVersion(Converter.convertVersionBeanToVersionH(versionBean));
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            if (e instanceof MyException) throw  (MyException)e;
            else throw new MyException(e);
        } finally {
            if (session != null) {
                //session.flush();
                session.clear();
                //session.close();
            }
        }
    }

    @Override
    public List<DocumentBean> getDocumentsByAuthor(String login) throws MyException {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            List<DocumentBean> documentBeans = new ArrayList<DocumentBean>();
            AuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO(session, type);
            Author author = authorDAO.getAuthorByLogin(login);
            DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(session, type);
            List<Document> docs = dao.getDocumentsByAuthorID(author.getId());
            for (Document doc : docs) {
                DocumentBean documentBean = Converter.convertDocumentHToDocumentBean(doc);
                documentBeans.add(documentBean);
            }
            tr.commit();
            return documentBeans;
        } catch (Exception e) {
            tr.rollback();
            if (e instanceof MyException) throw  (MyException)e;
            else throw new MyException(e);
        } finally {
            if (session != null) {
                //session.flush();
                session.clear();
                //session.close();
            }
        }
    }

    @Override
    public DocumentBean getDocumentByAuthorAndName(String login, long docNameCode) throws MyException {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(session, type);
            Document doc = dao.getDocumentByAuthorAndName(login, docNameCode);
            DocumentBean documentBean = Converter.convertDocumentHToDocumentBean(doc);
            tr.commit();
            return documentBean;
        } catch (NoSuchObjectInDB e){
            tr.rollback();
            throw e;
        } catch (Exception e) {
            tr.rollback();
            if (e instanceof MyException) throw  (MyException)e;
            else throw new MyException(e);
        } finally {
            if (session != null) {
                //session.flush();
                session.clear();
                //session.close();
            }
        }
    }

    @Override
    public AuthorBean getAuthorByLogin(String login) throws MyException {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            AuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO(session, type);
            Author author = authorDAO.getAuthorByLogin(login);
            tr.commit();
            return Converter.convertAuthorToAuthorBean(author);
        } catch (Exception e) {
            tr.rollback();
            if (e instanceof MyException) throw  (MyException)e;
            else throw new MyException(e);
        } finally {
            if (session != null) {
                // session.flush();
                session.clear();
                //session.close();
            }
        }
    }

    @Override
    public List<VersionBean> getVersionsOfDocument(String login, long docNameCode) throws MyException {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            List<VersionBean> versionBeans = new ArrayList<VersionBean>();
            DocumentDAO docDAO = DAOFactory.getInstance().getDocumentDAO(session, type);
            Document doc = docDAO.getDocumentByAuthorAndName(login, docNameCode);
            VersionDAO dao = DAOFactory.getInstance().getVersionDAO(session, type);
            List<Version> vers = dao.getVersionsOfDocument(doc.getId());
            for (Version ver : vers) {
                VersionBean versionBean = Converter.convertVersionHToVersionBean(ver);
                versionBeans.add(versionBean);
            }
            tr.commit();
            return versionBeans;
        } catch (NoSuchObjectInDB e){
            tr.rollback();
            throw e;
        } catch (Exception e) {
            tr.rollback();
            if (e instanceof MyException) throw  (MyException)e;
            else throw new MyException(e);
        } finally {
            if (session != null) {
                // session.flush();
                session.clear();
                //session.close();
            }
        }
    }

    @Override
    public VersionBean getVersion(String login, long docNameCode, long versName) throws MyException {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            DocumentDAO docDAO = DAOFactory.getInstance().getDocumentDAO(session, type);
            Document doc = docDAO.getDocumentByAuthorAndName(login, docNameCode);
            VersionDAO dao = DAOFactory.getInstance().getVersionDAO(session, type);
            Version ver = dao.getVersion(doc.getId(), versName);
            VersionBean versionBean = Converter.convertVersionHToVersionBean(ver);
            tr.commit();
            return versionBean;
        } catch (NoSuchObjectInDB e){
            tr.rollback();
            throw e;
        } catch (Exception e) {
            tr.rollback();
            if (e instanceof MyException) throw  (MyException)e;
            else throw new MyException(e);
        } finally {
            if (session != null) {
                //  session.flush();
                session.clear();
                //session.close();
            }
        }
    }

    @Override
    public void deleteDocument(String login, long docNameCode) throws MyException {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            DocumentDAO docDAO = DAOFactory.getInstance().getDocumentDAO(session, type);
            docDAO.deleteDocument(login, docNameCode);
            tr.commit();
        } catch (NoSuchObjectInDB e){
            tr.rollback();
            throw e;
        } catch (Exception e) {
            tr.rollback();
            if (e instanceof MyException) throw  (MyException)e;
            else throw new MyException(e);
        } finally {
            if (session != null) {
                //  session.flush();
                session.clear();
                // session.close();
            }
        }
    }

    @Override
    public void deleteVersion(long versName, long docCode, String login) throws MyException {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            VersionDAO verDAO = DAOFactory.getInstance().getVersionDAO(session, type);
            verDAO.deleteVersion(versName, docCode, login);
            tr.commit();
        } catch (NoSuchObjectInDB e){
            tr.rollback();
            throw e;
        } catch (Exception e) {
            tr.rollback();
            if (e instanceof MyException) throw  (MyException)e;
            else throw new MyException(e);
        } finally {
            if (session != null) {
                //  session.flush();
                session.clear();
                //session.close();
            }
        }
    }

    @Override
    public long getDocumentIDByCodeNameAndLogin(String login, long docName) throws MyException {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            DocumentDAO docDAO = DAOFactory.getInstance().getDocumentDAO(session, type);
            long id = docDAO.getDocumentID(login, docName);
            tr.commit();
            return id;
        } catch (NoSuchObjectInDB e){
            tr.rollback();
            throw e;
        } catch (Exception e) {
            tr.rollback();
            if (e instanceof MyException) throw  (MyException)e;
            else throw new MyException(e);
        } finally {
            if (session != null) {
                // session.flush();
                session.clear();
                //session.close();
            }
        }
    }

    @Override
    public String getVersionType(long versionName, long documentName, String login) throws MyException {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            VersionDAO verDAO = DAOFactory.getInstance().getVersionDAO(session, type);
            String t = verDAO.getVersionType(versionName, documentName, login);
            tr.commit();
            return t;
        } catch (Exception e) {
            tr.rollback();
            if (e instanceof MyException) throw  (MyException)e;
            else throw new MyException(e);
        } finally {
            if (session != null) {
                // session.flush();
                session.clear();
                //session.close();
            }
        }
    }

    @Override
    public void editVersionDescription(VersionBean versionBean) throws MyException {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            VersionDAO verDAO = DAOFactory.getInstance().getVersionDAO(session, type);
            String login = versionBean.getAuthor().getLogin();
            long codeDocName = versionBean.getDocument().getCodeDocumentName();
            long versionName = versionBean.getVersionName();
            String description = versionBean.getDescription();
            verDAO.updateVersionDescription(login, codeDocName, versionName, description);
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            if (e instanceof MyException) throw  (MyException)e;
            else throw new MyException(e);
        } finally {
            if (session != null) {
                // session.flush();
                session.clear();
                //session.close();
            }
        }
    }

    @Override
    public void editDocumentDescription(DocumentBean documentBean) throws MyException {
        Session session = sessionFactory.openSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            DocumentDAO verDAO = DAOFactory.getInstance().getDocumentDAO(session, type);
            String login = documentBean.getAuthor().getLogin();
            long codeDocName = documentBean.getCodeDocumentName();
            String description = documentBean.getDescription();
            verDAO.updateDocumentDescription(login, codeDocName, description);
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            if (e instanceof MyException) throw  (MyException)e;
            else throw new MyException(e);
        } finally {
            if (session != null) {
                // session.flush();
                session.clear();
                //session.close();
            }
        }
    }
}