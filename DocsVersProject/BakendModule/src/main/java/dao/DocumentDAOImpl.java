package dao;

import entities.Author;
import entities.Document;
import entities.Version;

import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 07.02.13
 * Time: 11:03
 * To change this template use File | Settings | File Templates.
 */
public class DocumentDAOImpl implements DocumentDAO {
    public static final String INSERT_INTO_DOCUMENTS_ID_NAME_VALUES = "insert into document (id, name) values (?,?)";
    private Connection conn;

    public DocumentDAOImpl() {

    }

    public DocumentDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Document> getAllDocuments() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addDocument(Document document) throws SQLException {
        if(document == null) {
            throw new IllegalArgumentException();
        }
//        conn.prepareStatement("insert into documents () values ()");
        conn.prepareStatement(INSERT_INTO_DOCUMENTS_ID_NAME_VALUES).executeUpdate();
    }

    @Override
    public void deleteDocument(Document document) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

//    @Override
//    public List<Document> getAllDocuments() {
//        Connection dbConnection = null;
//        PreparedStatement preparedStatement = null;
//        List<Document> docsList;
//        Document doc = new Document();
//
//        String selectSQL = "SELECT ID,AUTHOR_ID,DOCUMENT_NAME, DESCRIPTION FROM DOCUMENT";
//
//        try {
//            dbConnection = getConnection();
//            preparedStatement = dbConnection.prepareStatement(selectSQL);
//
//            // execute select SQL stetement
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//
//                doc.setId(rs.getLong("ID"));
////                doc.setAuthor(new );
//                String username = rs.getString("USERNAME");
//
////                System.out.println("userid : " + userid);
//                System.out.println("username : " + username);
//
//            }
//
//        } catch (SQLException e) {
//
//            System.out.println(e.getMessage());
//
//        } finally {
//
//            if (preparedStatement != null) {
////                preparedStatement.close();
//            }
//
//            if (dbConnection != null) {
////                dbConnection.close();
//            }
//
//        }
//
//    }

    @Override
    public List<Version> getVersionsOfDocument(Document document) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

//    @Override
//    public Author getAuthorByID(long id) {
//        Connection dbConnection = null;
//        PreparedStatement preparedStatement = null;
//
//        String selectSQL = "SELECT ID,LOGIN,PASSWORD FROM AUTHOR WHERE ID=?";
//
//        try {
//            dbConnection = getConnection();
//            preparedStatement = dbConnection.prepareStatement(selectSQL);
//            preparedStatement.setLong(1, id);
//            // execute select SQL stetement
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//
//                doc.setId(rs.getLong("ID"));
//                doc.setAuthor(new );
//                String username = rs.getString("USERNAME");
//
//                System.out.println("userid : " + userid);
//                System.out.println("username : " + username);
//
//            }
//
//        } catch (SQLException e) {
//
//            System.out.println(e.getMessage());
//
//        } finally {
//
//            if (preparedStatement != null) {
//                preparedStatement.close();
//            }
//
//            if (dbConnection != null) {
//                dbConnection.close();
//            }
//
//        }
//
//        return null;
//    }

    @Override
    public Author getAuthorByDocument(Document document) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Document> getDocumentsByAuthor(Author author) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
        } catch (InstantiationException e) {e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, user, pass);
    }
}
