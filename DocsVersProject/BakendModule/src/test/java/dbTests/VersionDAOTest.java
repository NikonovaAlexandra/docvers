package dbTests;

import com.ibatis.common.jdbc.ScriptRunner;
import dao.document.DocumentDAOImpl;
import dao.version.VersionDAOImpl;
import entities.Document;
import entities.Version;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import util.Queries;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 13.02.13
 * Time: 10:18
 * To change this template use File | Settings | File Templates.
 */
public class VersionDAOTest {

    private IDatabaseTester tester = null;
    private FlatXmlDataSet flatXMLDataSet;
    private IDatabaseConnection iConnection;
    @Before
    public void instantiate() throws Exception {
        flatXMLDataSet = new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("check_set.xml"));
        //Creating databse server instance
        tester = new JdbcDatabaseTester("org.h2.Driver", "jdbc:h2:mem:test", "root", "root");
        iConnection = tester.getConnection();
        String aSQLScriptFilePath = "C:\\Documents and Settings\\alni\\Desktop\\docvers-master\\DocsVersProject\\BakendModule\\src\\main\\resources\\databasescript_0_1.sql";

        //Setting DATA_FACTORY, so DBUnit will know how to work with specific HSQLDB data types
        iConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
        //Getting dataset for database initialization
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("full.xml"));
        Connection connection =  iConnection.getConnection();
        // Initialize object for ScripRunner
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

    @Ignore
    @Test
    public void getVersionsOfDocumentTest() throws SQLException, Exception {
        PreparedStatement ps =  iConnection.getConnection().prepareStatement(Queries.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID);
        ps.setLong(1,3);
        ITable template = flatXMLDataSet.getTable("version_of_document");
        ITable actual = DefaultColumnFilter.includedColumnsTable(iConnection.createTable("version", ps),
                template.getTableMetaData().getColumns());
        QueryDataSet qds = new QueryDataSet(iConnection);
        Assertion.assertEquals(template, actual);
    }


    @After
    public void cleaning() throws Exception {
        DatabaseOperation.CLOSE_CONNECTION(DatabaseOperation.DELETE_ALL);
        tester.onTearDown();
    }
}
