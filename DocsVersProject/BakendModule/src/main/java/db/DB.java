package db;

import com.ibatis.common.jdbc.ScriptRunner;
import dao.DAO;
import dao.DAOFactory;
import dao.document.DocumentDAOImpl;
import entities.Document;
import entities.Version;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import util.Queries;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 10.02.13
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
public class DB {

    private Connection connection = null;
    private ResultSet resultSet = null;
    private Statement statement = null;
    private PreparedStatement ps = null;

    public DB(){
        String aSQLScriptFilePath = "C:\\Documents and Settings\\alni\\Desktop\\docvers-master\\DocsVersProject\\BakendModule\\src\\main\\resources\\databasescript_0_1.sql";

        // Create MySql Connection

        try {
            connection = getConnection();
            statement = connection.createStatement();
//           Initialize object for ScripRunner
            ScriptRunner sr = new ScriptRunner(connection, false, false);

            // Give the input file to Reader
            Reader reader = new BufferedReader(
                    new FileReader(aSQLScriptFilePath));

            // Exctute script
            sr.runScript(reader);
            for(int i = 0; i<5;++i) {
                ps =  connection.prepareStatement(Queries.INSERT_INTO_AUTHOR);
                ps.setString(1, String.valueOf(("author"+i).toCharArray()));
                ps.setString(2,"pass"+i);
                ps.executeUpdate();
            }
            for(int i = 0; i<5;++i) {
                ps =  connection.prepareStatement(Queries.INSERT_INTO_DOCUMENT);
                ps.setLong(1,(5/(i+2)+1));
                ps.setString(2, "doc" + i);
                ps.setString(3, "descrition");
                ps.executeUpdate();
            }
            for(int i = 0; i<5;++i) {
                ps =  connection.prepareStatement(Queries.INSERT_INTO_VERSION);
                ps.setLong(1,3);
                ps.setLong(2,(5/(i+1)));
                ps.setDate(3, new Date(System.currentTimeMillis()));
                ps.setString(4,"descrition");
                ps.setString(5,"path");
                ps.executeUpdate();
            }
//            Connection jdbcConnection = DriverManager.getConnection("jdbc:h2:mem", "sa", "");
//            IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
//            IDataSet fullDataSet = connection.createDataSet();
//            FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full.xml"));
            resultSet = statement.executeQuery("select * from author");
            Outer.authorOut(resultSet);
            resultSet = statement.executeQuery("select * from document");
            Outer.documentOut(resultSet);
            resultSet = statement.executeQuery("select * from version");
            Outer.versionOut(resultSet);
            DAO dao = DAOFactory.getInstance().getDocumentDAO(connection);
            ((DocumentDAOImpl)dao).addDocument(new Document(0, 3, "doc", "desc"));
            //((DocumentDAOImpl)dao).deleteDocument(2);
            System.out.println("====================================");
            resultSet = statement.executeQuery("select * from document");
            Outer.documentOut(resultSet);
            resultSet = statement.executeQuery("select * from version");
            Outer.versionOut(resultSet);

            List<Document> l = ((DocumentDAOImpl)dao).getDocumentsByAuthorID(3);
            for(Document v : l) {
              System.out.println(v.getId()+" auth:"+v.getAuthorID()+" "+v.getName());
            }


            connection.close();
            statement.close();
            ps.close();

        } catch (Exception e) {
            System.err.println("Failed to Execute" + aSQLScriptFilePath
                    + " The error is " + e.getMessage());
        }



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
    public static void main(String[] args){
        DB d = new DB();

    }
}
