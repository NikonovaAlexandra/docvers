package daoTests.DocumentDAOTest;

import dao.DAOFactory;
import dao.document.DocumentDAO;
import dao.document.DocumentDAOImpl;
import entities.Document;
import exception.NoSuchObjectInDB;
import exception.NullConnectionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import service.QueriesSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 08.02.13
 * Time: 13:12
 * To change this template use File | Settings | File Templates.
 */
public class GetAllDocumentsByAuthorAndNameDAOTest {
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
    public void getAllDocumentsSuccessful() throws Exception {
        when(rs.next()).thenReturn(true);
        DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(conn);
        // when
        dao.getDocumentByAuthorAndName(anyString(), 0);
        // then
        verify(conn).prepareStatement(QueriesSQL.SELECT_FROM_DOCUMENT_WHERE_DOCUMENT_NAME_CODE_AND_AUTHOR_ID);
        verify(ps).setString(1, eq(anyString()));
        verify(ps).setLong(2, eq(anyLong()));
        verify(ps).executeQuery();
        verify(rs).next();
        verify(conn).commit();
        verify(ps).close();
        verify(rs).close();
    }

    @Test(expected = NullConnectionException.class)
    public void newDocumentDAOTest() throws Exception {
        DocumentDAO dao = new DocumentDAOImpl(null) {
        };
    }

    @After
    public void destroy() {
        reset(rs);
    }

}
