package dbTests;

import com.ibatis.common.jdbc.ScriptRunner;
import dao.document.DocumentDAOImpl;
import db.Outer;
import entities.Document;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.*;

import java.io.*;
import java.sql.*;

/**
* Created with IntelliJ IDEA.
* User: alni
* Date: 11.02.13
* Time: 11:24
* To change this template use File | Settings | File Templates.
*/
public class DocumentsDAOTest {

    private IDatabaseTester tester = null;
    private FlatXmlDataSet flatXMLDataSet;
    private IDatabaseConnection iConnection;
    @Before public void instantiate() throws Exception {
        new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("dbTests/full.xml"));
        new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("dbTests/111.xml"));
        flatXMLDataSet = new FlatXmlDataSetBuilder().build(new File("dbTests/111.xml"));
        //Creating databse server instance
        tester = new JdbcDatabaseTester("org.h2.Driver", "jdbc:h2:mem:test", "sa", "");
        iConnection = tester.getConnection();
        //Setting DATA_FACTORY, so DBUnit will know how to work with specific HSQLDB data types
        iConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
        //Getting dataset for database initialization
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File("dbTests/full.xml"));
        Connection connection =  iConnection.getConnection();
        // Initialize object for ScripRunner
        ScriptRunner sr = new ScriptRunner(connection, false, false);

        // Give the input file to Reader
        Reader reader = new BufferedReader(
                new FileReader(new File("BakendModule\\src\\main\\resources\\databasescript_0_1.sql")));

        // Exctute script
        sr.runScript(reader);

        //Initializing database
        tester.setDataSet(dataSet);
        tester.onSetup();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from author");
        Outer.authorOut(resultSet);
        connection.commit();

    }

//    @Test
//    public void getAllDocumentsTest() throws SQLException, Exception {
//        DocumentDAOImpl dao = new DocumentDAOImpl( iConnection.getConnection());
//        List<Document> a = dao.getAllDocuments();
//        PreparedStatement ps =  iConnection.getConnection().prepareStatement(Queries.SELECT_FROM_DOCUMENT);
//        ITable template = flatXMLDataSet.getTable("document_getall");
//        ITable actual = DefaultColumnFilter.includedColumnsTable( iConnection.createTable("document", ps),
//                template.getTableMetaData().getColumns());
//        Assertion.assertEquals(template, actual);
//    }

    @Test
    @Ignore
    public void addDocumentTest() throws SQLException, Exception {
        DocumentDAOImpl dao = new DocumentDAOImpl( iConnection.getConnection());
        Document doc = new Document(2,"doc25","descr");
        dao.addDocument(doc);
        ITable template = flatXMLDataSet.getTable("document_add");
        ITable actual = DefaultColumnFilter.includedColumnsTable( iConnection.createDataSet().getTable("document"),
                template.getTableMetaData().getColumns());
        Assertion.assertEquals(template, actual);
    }

    @Test
//    @Ignore
    public void deleteDocumentTest() throws SQLException, Exception {
        DocumentDAOImpl dao = new DocumentDAOImpl( iConnection.getConnection());
        dao.deleteDocument(3);
        ITable template = flatXMLDataSet.getTable("document_delete");
        ITable actual = DefaultColumnFilter.includedColumnsTable( iConnection.createDataSet().getTable("document"),
                template.getTableMetaData().getColumns());
        Assertion.assertEquals(template, actual);
        template = new FlatXmlDataSetBuilder().build(new File("dbTests/111.xml")).getTable("document_version_delete");
        actual = DefaultColumnFilter.includedColumnsTable( iConnection.createDataSet().getTable("version"),
                template.getTableMetaData().getColumns());
        Assertion.assertEquals(template, actual);
    }

//    @Test
//    public void getDocumentsByAuthorTest() throws SQLException, Exception {
//        DocumentDAOImpl dao = new DocumentDAOImpl( iConnection.getConnection());
//        List<Document> l = dao.getDocumentsByAuthorID(2);
//
//        PreparedStatement ps =  iConnection.getConnection().prepareStatement(Queries.SELECT_FROM_DOCUMENT_WHERE_AUTHOR_ID);
//        ps.setLong(1,2);
//        ITable template = flatXMLDataSet.getTable("document_getbyauthor");
//        ITable actual = DefaultColumnFilter.includedColumnsTable( iConnection.createTable("document", ps),
//                template.getTableMetaData().getColumns());
//        Assertion.assertEquals(template, l);
//    }
    @After
    public void cleaning() throws Exception {
        DatabaseOperation.CLOSE_CONNECTION(DatabaseOperation.DELETE_ALL);
        tester.onTearDown();
    }
}
