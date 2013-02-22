package db.old; /**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 19.02.13
 * Time: 9:37
 * To change this template use File | Settings | File Templates.
 */

import dao.DAOFactory;
import dao.document.DocumentDAO;
import entities.Document;
import exception.DAOException;
import exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloWorld {
    public Logger logger = LoggerFactory.getLogger(HelloWorld.class);

    public static void main(String[] args) throws SQLException, DAOException, SystemException {
        HelloWorld hw = new HelloWorld();
        Connection conn = hw.getConnection();
        DocumentDAO documentDAO = DAOFactory.getInstance().getDocumentDAO(conn);
//        Document doc = new Document(2, "doc1", "descr");
//        documentDAO.addDocument(doc);

//        documentDAO = DAOFactory.getInstance().getDocumentDAO(conn);
        Document doc = new Document(35, "doc1258", "descr");
        documentDAO.addDocument(doc);
    }

    private Connection getConnection() throws SQLException {
        ResourceBundle resource =
                ResourceBundle.getBundle("database");
        String url = resource.getString("url");
        String driver = resource.getString("driver");
        String user = resource.getString("user");
        String pass = resource.getString("password");
        try {
            Class.forName(driver).newInstance();
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver isn't loaded!");
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        }
        return DriverManager.getConnection(url, user, pass);
    }
}