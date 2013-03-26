package daoTests.VersionDAOTest;

import dao.DAOFactory;
import dao.document.DocumentDAO;
import dao.version.VersionDAO;
import daoTests.EntitiesFactory;
import entities.Document;
import entities.Version;
import exception.NoSuchObjectInDB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.QueriesSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 26.03.13
 * Time: 9:02
 * To change this template use File | Settings | File Templates.
 */
public class deleteVersionDAOTest {

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
    public void deleteVersionSuccessful() throws Exception {

        when(ps.executeUpdate()).thenReturn(1);
        VersionDAO dao = DAOFactory.getInstance().getVersionDAO(conn);
        // when
        dao.deleteVersion(0, 0, "");
        // than
        verify(conn).prepareStatement(QueriesSQL.DELETE_FROM_VERSION_WHERE_VERSION_NAME_AND_DOC_AND_LOGIN);
        verify(ps).setLong(1, 0);
        verify(ps).setLong(2, 0);
        verify(ps).setString(3, "");
        verify(ps).executeUpdate();
        verify(ps).close();

    }

    @Test(expected = NoSuchObjectInDB.class)
    public void deleteVersionNothingToDel() throws Exception {

        when(ps.executeUpdate()).thenReturn(0);
        VersionDAO dao = DAOFactory.getInstance().getVersionDAO(conn);
        // when
        dao.deleteVersion(0, 0, "");
        // than
        verify(conn).prepareStatement(QueriesSQL.DELETE_FROM_VERSION_WHERE_VERSION_NAME_AND_DOC_AND_LOGIN);
        verify(ps).setLong(1, eq(anyLong()));
        verify(ps).setLong(2, eq(anyLong()));
        verify(ps).setString(3, eq(anyString()));
        verify(ps).executeUpdate();
        verify(ps).close();

    }
    @After
    public void destroy() {
        reset(ps);
    }
}
