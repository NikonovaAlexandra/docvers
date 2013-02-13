package dbTests;

import com.ibatis.common.jdbc.ScriptRunner;
import dao.document.DocumentDAOImpl;
import entities.Document;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.junit.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
* Created with IntelliJ IDEA.
* User: alni
* Date: 11.02.13
* Time: 11:24
* To change this template use File | Settings | File Templates.
*/
public class getAllDocumentsTest {

    private IDatabaseTester tester = null;

    @Before public void instantiate() throws Exception {
        //Creating databse server instance
        tester = new JdbcDatabaseTester("org.h2.Driver", "jdbc:h2:mem", "sa", "");
        String aSQLScriptFilePath = "C:\\Documents and Settings\\alni\\Desktop\\docvers-master\\DocsVersProject\\BakendModule\\src\\main\\resources\\databasescript_0_1.sql";
        //Creating tables



        //Setting DATA_FACTORY, so DBUnit will know how to work with specific HSQLDB data types
        tester.getConnection().getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
        //Getting dataset for database initialization
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("full.xml"));
        Connection connection = tester.getConnection().getConnection();
        //           Initialize object for ScripRunner
        ScriptRunner sr = new ScriptRunner(connection, false, false);

        // Give the input file to Reader
        Reader reader = new BufferedReader(
                new FileReader(aSQLScriptFilePath));

        // Exctute script
        sr.runScript(reader);

        //Initializing database
        tester.setDataSet(dataSet);
        tester.onSetup();
    }

    @Test
    public void getAllDocumentsTest() throws SQLException, Exception {
        DocumentDAOImpl dao = new DocumentDAOImpl(tester.getConnection().getConnection());
        List<Document> a = dao.getAllDocuments();
        ITable template = new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().
                getResourceAsStream("check_set.xml")).getTable("document_getall");
        ITable actual = DefaultColumnFilter.includedColumnsTable(tester.getConnection().createDataSet().getTable("document"),
                template.getTableMetaData().getColumns());
        Assertion.assertEquals(template, actual);
    }

    @Test
    public void addDocumentTest() throws SQLException, Exception {
        DocumentDAOImpl dao = new DocumentDAOImpl(tester.getConnection().getConnection());
        Document doc = new Document(5,2,"doc25","descr");
        dao.addDocument(doc);
        ITable template = new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().
                getResourceAsStream("check_set.xml")).getTable("document_add");
        ITable actual = DefaultColumnFilter.includedColumnsTable(tester.getConnection().createDataSet().getTable("document"),
                template.getTableMetaData().getColumns());
        Assertion.assertEquals(template, actual);
    }

    @Test
    public void deleteDocumentTest() throws SQLException, Exception {
        DocumentDAOImpl dao = new DocumentDAOImpl(tester.getConnection().getConnection());
        dao.deleteDocument(3);
        ITable template = new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().
                getResourceAsStream("check_set.xml")).getTable("document_delete");
        ITable actual = DefaultColumnFilter.includedColumnsTable(tester.getConnection().createDataSet().getTable("document"),
                template.getTableMetaData().getColumns());
        Assertion.assertEquals(template, actual);
    }

//    @Test
//    public void getDocumentsByAuthorTest() throws SQLException, Exception {
//        DocumentDAOImpl dao = new DocumentDAOImpl(tester.getConnection().getConnection());
//        List<Document> l = dao.getDocumentsByAuthorID(3);
//        ITable template = new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().
//                getResourceAsStream("check_set.xml")).getTable("document_getbyauthor");
//        ITable actual = DefaultColumnFilter.includedColumnsTable(tester.getConnection().createDataSet().getTable("document"),
//                template.getTableMetaData().getColumns());
//        Assertion.assertEquals(template, actual);
//    }
}
