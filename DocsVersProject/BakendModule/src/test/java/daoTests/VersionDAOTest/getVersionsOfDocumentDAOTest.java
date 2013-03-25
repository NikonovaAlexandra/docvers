package daoTests.VersionDAOTest;

import dao.DAOFactory;
import dao.version.VersionDAO;
import daoTests.EntitiesFactory;
import entities.Document;
import entities.Version;
import exception.NoSuchObjectInDB;
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
* Time: 10:09
* To change this template use File | Settings | File Templates.
*/
public class getVersionsOfDocumentDAOTest {
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
    public void getVersionsOfDocumentSuccessful() throws Exception {
        when(rs.next()).thenReturn(true).thenReturn(false);
        // there is no documents in database
        VersionDAO dao = DAOFactory.getInstance().getVersionDAO(conn);
        // when
        dao.getVersionsOfDocument(anyLong());
        // then
        verify(conn).prepareStatement(QueriesSQL.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID);
        verify(ps).setLong(1, eq(anyLong()));
        verify(ps).executeQuery();
        verify(rs, atLeast(1)).next();
        verify(ps).close();
        verify(rs).close();

    }

    @Test(expected = NoSuchObjectInDB.class)
    public void getVersionsOfDocumentNo() throws Exception {
        when(rs.next()).thenReturn(false);
        // there is no documents in database
        VersionDAO dao = DAOFactory.getInstance().getVersionDAO(conn);
        // when
        dao.getVersionsOfDocument(anyLong());
        // then
        verify(conn).prepareStatement(QueriesSQL.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID);
        verify(ps).setLong(1, eq(anyLong()));
        verify(ps).executeQuery();
        verify(rs).next();
        verify(ps).close();
        verify(rs).close();

    }

    @After
    public void destroy() {
        reset(rs);
        reset(ps);
    }

}
