package daoTests.VersionDAOTest;

import dao.DAOFactory;
import dao.document.DocumentDAO;
import dao.version.VersionDAO;
import entities.Version;
import exception.NoSuchObjectInDB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.QueriesSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 26.03.13
 * Time: 9:03
 * To change this template use File | Settings | File Templates.
 */
public class GetVersionDAOTest {

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
    public void getVersionSuccessful() throws Exception {
        when(rs.next()).thenReturn(true).thenReturn(false);
        VersionDAO dao = DAOFactory.getInstance().getVersionDAO(conn);
        // when
        dao.getVersion(0, 0);
        // then
        verify(conn).prepareStatement(QueriesSQL.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID_AND_VERSION_NAME);
        verify(ps).setLong(1, 0);
        verify(ps).setLong(2, 0);
        verify(ps).executeQuery();
        verify(rs, atLeast(1)).next();
        verify(ps).close();
        verify(rs).close();

    }

    @Test(expected = NoSuchObjectInDB.class)
    public void getVersionNo() throws Exception {
        when(rs.next()).thenReturn(false);
        VersionDAO dao = DAOFactory.getInstance().getVersionDAO(conn);
        // when
        dao.getVersion(0, 0);
        // then
        verify(conn).prepareStatement(QueriesSQL.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID_AND_VERSION_NAME);
        verify(ps).setLong(1, 0);
        verify(ps).setLong(2, 0);
        verify(ps).executeQuery();
        verify(rs, atLeast(1)).next();
        verify(ps).close();
        verify(rs).close();

    }

    @After
    public void destroy() {
        reset(rs);
    }
}
