package daoTests.DocumentDAOTest;

import dao.DAOFactory;
import dao.document.DocumentDAO;
import exception.NoSuchObjectInDB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import service.QueriesSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 25.03.13
 * Time: 23:48
 * To change this template use File | Settings | File Templates.
 */
public class GetDocumentIdDAOTest {
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
        public void getDocumentsByAuthorSuccessful() throws Exception {
            when(rs.next()).thenReturn(true);
            when(rs.getLong("id")).thenReturn((long)1);
            DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(conn);
            // when
            dao.getDocumentID(anyString(), 0);
            // then
            verify(conn).prepareStatement(QueriesSQL.SELECT_ID_FROM_DOCUMENT);
            verify(ps).setString(1, eq(anyString()));
            verify(ps).setLong(2, eq(anyLong()));
            verify(ps).executeQuery();
            verify(rs).next();
            verify(rs).getLong("id");
            verify(ps).close();
        }

    @Test(expected = NoSuchObjectInDB.class)
    public void getDocumentsByAuthorNo1() throws Exception {
        when(rs.next()).thenReturn(false);
        DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(conn);
        // when
        dao.getDocumentID(anyString(), 0);
        // then
        verify(conn).prepareStatement(QueriesSQL.SELECT_ID_FROM_DOCUMENT);
        verify(ps).setString(1, anyString());
        verify(ps).setLong(2, anyLong());
        verify(ps).executeQuery();
        verify(rs).next();
        verify(rs).getLong("id");
        verify(ps).close();
    }

        @After
        public void destroy() {
            reset(rs);
            reset(ps);
        }


}
