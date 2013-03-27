package daoTests.DocumentDAOTest;

import dao.DAOFactory;
import dao.document.DocumentDAO;
import daoTests.EntitiesFactory;
import entities.Document;
import exception.NoSuchObjectInDB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.QueriesSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 11.02.13
 * Time: 8:27
 * To change this template use File | Settings | File Templates.
 */
public class DeleteDocumentDAOTest {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;


    @Before
    public void init () throws Exception {
        conn = mock(Connection.class);
        ps = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

    }

    @Test
    public void deleteDocumentSuccessful() throws Exception {

        when(ps.executeUpdate()).thenReturn(1);
        DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(conn);
        // when
        Document doc = EntitiesFactory.createNewDocument();
        dao.deleteDocument(anyString(), 0);
        // than
        verify(conn).prepareStatement(QueriesSQL.DELETE_FROM_DOCUMENT_WHERE_AUTHOR_ID_AND_CODE);
        verify(ps).setString(1, eq(anyString()));
        verify(ps).setLong(2, eq(anyLong()));
        verify(ps).executeUpdate();
        verify(ps).close();

    }

    @Test(expected = NoSuchObjectInDB.class)
    public void deleteDocumentNothingToDel() throws Exception {

        when(ps.executeUpdate()).thenReturn(0);
        DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(conn);
        // when
        Document doc = EntitiesFactory.createNewDocument();
        dao.deleteDocument(anyString(), 0);
        // than
        verify(conn).prepareStatement(QueriesSQL.DELETE_FROM_DOCUMENT_WHERE_AUTHOR_ID_AND_CODE);
        verify(ps).setString(1, anyString());
        verify(ps).setLong(2, anyLong());
        verify(ps).executeUpdate();
        verify(ps).close();

    }
    @After
    public void destroy() {
        reset(ps);
    }
}
