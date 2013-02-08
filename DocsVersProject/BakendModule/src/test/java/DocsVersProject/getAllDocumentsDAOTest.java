package DocsVersProject;

import dao.DocumentDAO;
import dao.DocumentDAOImpl;
import entities.Document;
import exception.NullConnectionException;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 08.02.13
 * Time: 13:12
 * To change this template use File | Settings | File Templates.
 */
public class getAllDocumentsDAOTest {
    @Test
    public void getAllDocumentsSuccessful() throws Exception {
        Connection conn = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ResultSet rs = Mockito.mock(ResultSet.class);
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        // there is no documents in database
        DocumentDAO dao = new DocumentDAOImpl(conn);
        // when
        dao.getAllDocuments();
        // then
        verify(conn).prepareStatement(DocumentDAOImpl.SELECT_FROM_DOCUMENT);
        verify(ps).executeQuery();
        verify(rs).next();
        verify(ps).close();
        verify(rs).close();
    }

    @Test(expected = NullConnectionException.class)
    public void newDocumentDAOTest() throws Exception{
        DocumentDAO dao = new DocumentDAOImpl(null);
    }

}
