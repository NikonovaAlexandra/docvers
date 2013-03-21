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
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import service.SessionFactoryUtil;

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
        try {
            DocumentDAO documentDAO = DAOFactory.getInstance().getDocumentDAO(session, type);
            documentDAO.addDocument(Converter.convertDocumentBeanToDocumentH(documentBean));
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
        try {
            VersionDAO versionDAO = DAOFactory.getInstance().getVersionDAO(session, type);
            long id = versionDAO.getLastVersionNameInfo(docID);
            return id;
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
        try {
            VersionDAO versionDAO = DAOFactory.getInstance().getVersionDAO(session, type);
            versionDAO.addVersion(Converter.convertVersionBeanToVersionH(versionBean));
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
        try {
            List<DocumentBean> documentBeans = new ArrayList<DocumentBean>();
            AuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO(session, type);
            Author author = authorDAO.getAuthorByLogin(login);
            DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(session, type);
            List<Document> docs = dao.getDocumentsByAuthorID(author.getId());
            for (Document doc : docs) {
                DocumentBean documentBean = Converter.convertDocumentHToDocumentBean(doc);
                documentBeans.add(documentBean);
            }

            return documentBeans;
        } finally {
            if (session != null) {
                //session.flush();
                session.clear();
                //session.close();
            }
        }
    }

    @Override
    public DocumentBean getDocumentsByAuthorAndName(String login, long docNameCode) throws MyException {
        Session session = sessionFactory.openSession();
        try {
            DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(session, type);
            Document doc = dao.getDocumentByAuthorAndName(login, docNameCode);
            DocumentBean documentBean = Converter.convertDocumentHToDocumentBean(doc);
            return documentBean;
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
        try {
            AuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO(session, type);
            Author author = authorDAO.getAuthorByLogin(login);
            return Converter.convertAuthorToAuthorBean(author);
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
        try {
            List<VersionBean> versionBeans = new ArrayList<VersionBean>();
            DocumentDAO docDAO = DAOFactory.getInstance().getDocumentDAO(session, type);
            Document doc = docDAO.getDocumentByAuthorAndName(login, docNameCode);
            VersionDAO dao = DAOFactory.getInstance().getVersionDAO(session, type);
            List<Version> vers = dao.getVersionsOfDocument(doc.getId());
            for (Version ver : vers) {
                VersionBean versionBean = Converter.convertVersionHToVersionBean(ver);
                versionBeans.add(versionBean);
            }
            return versionBeans;
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
        try {
            DocumentDAO docDAO = DAOFactory.getInstance().getDocumentDAO(session, type);
            Document doc = docDAO.getDocumentByAuthorAndName(login, docNameCode);
            VersionDAO dao = DAOFactory.getInstance().getVersionDAO(session, type);
            Version ver = dao.getVersion(doc.getId(), versName);
            VersionBean versionBean = Converter.convertVersionHToVersionBean(ver);
            return versionBean;
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
        try {
            DocumentDAO docDAO = DAOFactory.getInstance().getDocumentDAO(session, type);
            docDAO.deleteDocument(login, docNameCode);
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
        try {
            VersionDAO verDAO = DAOFactory.getInstance().getVersionDAO(session, type);
            verDAO.deleteVersion(versName, docCode, login);
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
        try {
            DocumentDAO docDAO = DAOFactory.getInstance().getDocumentDAO(session, type);
            long id = docDAO.getDocumentID(login, docName);
            return id;
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
        try {
            VersionDAO verDAO = DAOFactory.getInstance().getVersionDAO(session, type);
            String t = verDAO.getVersionType(versionName, documentName, login);
            return t;
        } finally {
            if (session != null) {
               // session.flush();
                session.clear();
               //session.close();
            }
        }
    }
}
