package daoTests.DocumentDAOTest;

import dao.DAOFactory;
import dao.document.DocumentDAO;
import daoTests.EntitiesFactory;
import entities.Author;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import util.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 11.02.13
 * Time: 9:08
 * To change this template use File | Settings | File Templates.
 */
public class getDocumentsByAuthorTest {

    @Test
    public void getDocumentsByAuthorSuccessful() throws Exception {
        Connection conn = Mockito.mock(Connection.class);
        PreparedStatement ps = Mockito.mock(PreparedStatement.class);
        ResultSet rs = Mockito.mock(ResultSet.class);
        Author author = EntitiesFactory.createNewAuthor();
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        // there is no documents in database
        DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(conn);
        // when
        dao.getDocumentsByAuthorID(author.getId());
        // then
        verify(conn).prepareStatement(Queries.SELECT_FROM_DOCUMENT_WHERE_AUTHOR_ID);
        verify(ps).setLong(1, author.getId());
        verify(ps).executeQuery();
        verify(rs).next();
        verify(ps).close();
        verify(rs).close();
    }

}
