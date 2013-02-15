package dbTests;

import com.ibatis.common.jdbc.ScriptRunner;
import dao.DAO;
import dao.DAOFactory;
import dao.author.AuthorDAO;
import dao.author.AuthorDAOImpl;
import dao.document.DocumentDAOImpl;
import dao.version.VersionDAOImpl;
import db.Outer;
import entities.Author;
import entities.Document;
import entities.Version;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
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
import java.util.List;

/**
* Created with IntelliJ IDEA.
* User: alni
* Date: 11.02.13
* Time: 11:24
* To change this template use File | Settings | File Templates.
*/
public class EntitiesDAOTest {

    private IDatabaseTester tester = null;
    private FlatXmlDataSet flatXMLDataSet;
    private IDatabaseConnection iConnection;
    private DAO dao;
    private ITable template, actual;
    private String path = "BakendModule/src/test/java/dbTests/";
    @Before public void instantiate() throws Exception {

        //Creating databse server instance
        tester = new JdbcDatabaseTester("org.h2.Driver", "jdbc:h2:mem:test", "sa", "");
        iConnection = tester.getConnection();
        //Setting DATA_FACTORY, so DBUnit will know how to work with specific HSQLDB data types
        iConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
        //Getting dataset for database initialization
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File(path+"dataset_before.xml"));
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
        connection.commit();

    }

    @Test
    public void getAllDocumentsTest() throws SQLException, Exception {
        flatXMLDataSet = new FlatXmlDataSetBuilder().build(new File(path+"dataset_before.xml"));
        dao = new DocumentDAOImpl(iConnection.getConnection());
        List<Document> l = ((DocumentDAOImpl)dao).getAllDocuments();
        template = flatXMLDataSet.getTable("document");
        assertDocuments(l, template);

    }

    @Test
    public void addDocumentTest() throws SQLException, Exception {
        flatXMLDataSet = new FlatXmlDataSetBuilder().build(new File(path+"dataset_after_adding.xml"));
        dao = new DocumentDAOImpl( iConnection.getConnection());
        Document doc = new Document(2,"doc25","descr");
        ((DocumentDAOImpl)dao).addDocument(doc);
        template = flatXMLDataSet.getTable("document");
        actual = DefaultColumnFilter.includedColumnsTable( iConnection.createDataSet().getTable("document"),
                template.getTableMetaData().getColumns());
        Assertion.assertEquals(template, actual);
    }

    @Test
    public void deleteDocumentTest() throws SQLException, Exception {
        flatXMLDataSet = new FlatXmlDataSetBuilder().build(new File(path+"dataset_after_deleting_document.xml"));
        dao = new DocumentDAOImpl( iConnection.getConnection());
        ((DocumentDAOImpl)dao).deleteDocument(3);
        template = flatXMLDataSet.getTable("document");
        actual = DefaultColumnFilter.includedColumnsTable( iConnection.createDataSet().getTable("document"),
                template.getTableMetaData().getColumns());
        Assertion.assertEquals(template, actual);
        template = flatXMLDataSet.getTable("version");
        actual = DefaultColumnFilter.includedColumnsTable( iConnection.createDataSet().getTable("version"),
                template.getTableMetaData().getColumns());
        Assertion.assertEquals(template, actual);
    }

    @Test
    public void getDocumentsByAuthorTest() throws SQLException, Exception {
        flatXMLDataSet = new FlatXmlDataSetBuilder().build(new File(path+"dataset_select_by_author_id"));
        dao = new DocumentDAOImpl( iConnection.getConnection());
        List<Document> l = ((DocumentDAOImpl)dao).getDocumentsByAuthorID(2);
        template = flatXMLDataSet.getTable("document");
        int i =0;
        assertDocuments(l, template);
    }

    @Test
    public void getAuthorByIDTest() throws Exception {
        flatXMLDataSet = new FlatXmlDataSetBuilder().build(new File(path+"dataset_get_author_by_id"));
        dao = new AuthorDAOImpl( iConnection.getConnection());
        Author author = ((AuthorDAOImpl)dao).getAuthorByID(2);
        template = flatXMLDataSet.getTable("author");
        assertAuthor(author, template);
    }
    @Test
    public void getAuthorByLoginTest() throws Exception {
        flatXMLDataSet = new FlatXmlDataSetBuilder().build(new File(path+"dataset_get_author_by_login"));
        dao = new AuthorDAOImpl( iConnection.getConnection());
        Author author = ((AuthorDAOImpl)dao).getAuthorByLogin("author2");
        template = flatXMLDataSet.getTable("author");
        assertAuthor(author, template);
    }
    @Test
    public void getAuthorByDocumentIDTest() throws Exception {
        flatXMLDataSet = new FlatXmlDataSetBuilder().build(new File(path+"dataset_get_author_by_document_id"));
        dao = new AuthorDAOImpl( iConnection.getConnection());
        Author author = ((AuthorDAOImpl)dao).getAuthorByDocumentID(2);
        template = flatXMLDataSet.getTable("author");
        assertAuthor(author, template);
    }
    @Test
    public void getVersionsOfDocumentTest() throws Exception {
        flatXMLDataSet = new FlatXmlDataSetBuilder().build(new File(path+"dataset_get_versions_by_document"));
        dao = new VersionDAOImpl( iConnection.getConnection());
        List<Version> versions = ((VersionDAOImpl)dao).getVersionsOfDocument(3);
        template = flatXMLDataSet.getTable("version");
        assertVersions(versions, template);
    }
    @After
    public void cleaning() throws Exception {
        DatabaseOperation.CLOSE_CONNECTION(DatabaseOperation.DELETE_ALL);
        tester.onTearDown();
    }
    private void assertDocuments(List<Document> docs, ITable template) throws DataSetException {
        int i = 0;
        assert(docs.size()==template .getRowCount());
        for(Document doc: docs) {
            assert(String.valueOf(doc.getId()).equals(template.getValue(i,"id")));
            assert(template .getValue(i, "document_name").equals(doc.getName()));
            assert(String.valueOf(doc.getAuthorID()).equals(template.getValue(i,"author_id")));
            assert (template .getValue(i, "description").equals(doc.getDescription()));
            i++;
        }
    }
    private void assertAuthor(Author author, ITable template) throws DataSetException {
        assert(template.getRowCount()==1);
        assert(String.valueOf(author.getId()).equals(template.getValue(0,"id")));
        assert(template.getValue(0,"login").equals(author.getLogin()));
        assert (template.getValue(0,"password").equals(author.getPassword()));
    }
    private void assertVersions(List<Version> versions, ITable template) throws DataSetException {
        int i = 0;
        assert(versions.size()==template.getRowCount());
        for(Version ver: versions) {
            assert(String.valueOf(ver.getId()).equals(template.getValue(i,"id")));
            assert(String.valueOf(ver.getDocumentID()).equals(template.getValue(i,"document_id")));
            assert(String.valueOf(ver.getAuthorID()).equals(template.getValue(i,"author_id")));
            assert(String.valueOf(ver.getDate()).equals(template.getValue(i, "date")));
            assert(String.valueOf(ver.getAuthorID()).equals(template.getValue(i,"author_id")));
            assert (template .getValue(i, "document_path").equals(ver.getDocumentPath()));
            assert (String.valueOf(ver.getVersionDescription()).equals(template .getValue(i, "version_description")));
            i++;
        }
    }

}