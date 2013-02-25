package util;

import beans.Converter;
import beans.DocumentBean;
import dao.DAOFactory;
import dao.author.AuthorDAO;
import dao.document.DocumentDAO;
import entities.Author;
import entities.Document;
import exception.*;

import java.rmi.NoSuchObjectException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 17.02.13
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */
public class DBOperations {
    private static DBOperations instance;

    public static synchronized DBOperations getInstance() {
        if (instance == null) {
            instance = new DBOperations();
        }
        return instance;
    }

    public void addDocument(DocumentBean documentBean) throws BusinessException, SystemException {
        try {
            Connection conn = ConnectionFactory.getInstance().getConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            AuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO(conn);
            Author author = authorDAO.getAuthorByLogin(documentBean.getAuthorBean().getLogin());
            DocumentDAO documentDAO = DAOFactory.getInstance().getDocumentDAO(conn);
            Document doc = new Document();
            doc.setName(documentBean.getName());
            doc.setDescription(documentBean.getDescription());
            doc.setAuthorID(author.getId());
            documentDAO.addDocument(doc);

            conn.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<DocumentBean> getAllDocuments() throws BusinessException, SystemException {
        List<DocumentBean> documentBeans = new ArrayList<DocumentBean>();
        try {
            Connection conn = ConnectionFactory.getInstance().getConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(conn);
            AuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO(conn);
            List<Document> docs = dao.getAllDocuments();

            for (Document doc : docs) {
                Author author = authorDAO.getAuthorByID(doc.getAuthorID());
                DocumentBean documentBean = Converter.convertDocumentToDocumentBean(doc, author);
                documentBeans.add(documentBean);
            }

            conn.close();
            return documentBeans;
        } catch (SQLException e) {
            throw new DAOException(e);
        }

    }

    public Author getAuhorByLogin(String login) throws BusinessException, SystemException {
        Author author;
        try {
            Connection conn = ConnectionFactory.getInstance().getConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            AuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO(conn);
            author = authorDAO.getAuthorByLogin(login);
            conn.close();
            return author;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
