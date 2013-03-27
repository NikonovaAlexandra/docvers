package daoTests.DocumentDAOTest;

import dao.DAOFactory;
import dao.document.DocumentDAO;
import daoTests.EntitiesFactory;
import entities.Author;
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
* Date: 11.02.13
* Time: 9:08
* To change this template use File | Settings | File Templates.
*/
public class GetDocumentsByAuthorTest {
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

    @Test(expected = NoSuchObjectInDB.class)
    public void getDocumentsByAuthorSuccessful() throws Exception {
        DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(conn);
        // when
        dao.getDocumentsByAuthorID(anyLong());
        // then
        verify(conn).prepareStatement(QueriesSQL.SELECT_FROM_DOCUMENT_WHERE_AUTHOR_ID);
        verify(ps).setLong(1, anyLong());
        verify(ps).executeQuery();
        verify(rs).next();
        verify(ps).close();
        verify(rs).close();
    }

    @After
    public void destroy() {
        reset(rs);
    }

}
