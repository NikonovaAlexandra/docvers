package daoTests.VersionDAOTest;

import dao.DAOFactory;
import dao.version.VersionDAO;
import entities.Version;
import exception.MyException;
import exception.NoSuchObjectInDB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.QueriesSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.reset;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 26.03.13
 * Time: 9:02
 * To change this template use File | Settings | File Templates.
 */
public class AddVersionDAOTest {
    private Connection conn;
    private PreparedStatement ps;



    @Before
    public void init () throws Exception {
        conn = mock(Connection.class);
        ps = mock(PreparedStatement.class);
        when(conn.prepareStatement(anyString())).thenReturn(ps);


    }
    @Test
    public void addVersionSuccessful() throws Exception {
        // there is no documents in database
        Version version = new Version();
        VersionDAO dao = DAOFactory.getInstance().getVersionDAO(conn);
        // when
        dao.addVersion(version);
        // then
        verify(conn).prepareStatement(QueriesSQL.UPDATE_VERSION_SET_IS_RELEASED);
        verify(ps).setBoolean(1, true);
        verify(ps, times(2)).setLong(2, version.getDocumentID());
        verify(ps).setBoolean(3, true);
        verify(ps, times(2)).executeUpdate();
        verify(conn).prepareStatement(QueriesSQL.INSERT_INTO_VERSION);
        verify(ps).setLong(1, version.getDocumentID());
        verify(ps).setTimestamp(3, version.getDate());
        verify(ps).setString(4, version.getVersionDescription());
        verify(ps).setString(5, version.getDocumentPath());
        verify(ps).setBoolean(6, false);
        verify(ps).setString(7, version.getVersionType());
        verify(ps).setLong(8, version.getVersionName());
        verify(ps).close();

    }

    @Test(expected = IllegalArgumentException.class)
    public void addVersionsNull() throws Exception {
        // there is no versions in database
        Version version = null;
        VersionDAO dao = DAOFactory.getInstance().getVersionDAO(conn);
        dao.addVersion(version);
    }

    @After
    public void destroy() {
       reset(ps);
    }

}
