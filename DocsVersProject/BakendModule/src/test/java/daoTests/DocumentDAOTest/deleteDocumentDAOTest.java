package daoTests.DocumentDAOTest;

import daoTests.EntitiesFactory;
import dao.DAOFactory;
import dao.document.DocumentDAO;
import entities.Document;
import org.junit.Test;
import org.mockito.Mockito;
import util.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 11.02.13
 * Time: 8:27
 * To change this template use File | Settings | File Templates.
 */
public class deleteDocumentDAOTest {

    @Test
    public void deleteDocumentSuccessful() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        // there is no documents in database
        DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(conn);
        // when
        Document doc = EntitiesFactory.createNewDocument();
        dao.deleteDocument(doc.getId());
        // than
        verify(conn).prepareStatement(Queries.DELETE_FROM_DOCUMENT_WHERE_ID);
        verify(ps).setLong(1, doc.getId());
        verify(ps).executeUpdate();
        verify(ps).close();

    }

}
