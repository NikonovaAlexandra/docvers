package daoTests.DocumentDAOTest;

import dao.DAOFactory;
import dao.document.DocumentDAO;
import entities.Author;
import entities.Document;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
* Date: 07.02.13
* Time: 10:56
* To change this template use File | Settings | File Templates.
*/
public class AddNewDocumentDAOTest {
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
    public void createNewDocumentSuccessful() throws Exception {

        // there is no documents in database
        DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(conn);
        Document doc = daoTests.EntitiesFactory.createNewDocument();
        dao.addDocument(doc);
        // then
        verify(conn).prepareStatement(QueriesSQL.INSERT_INTO_DOCUMENT_AUTHOR_NAME_DESCRIPTION_VALUES);
        verify(ps).setLong(1, doc.getAuthorID());
        verify(ps).setString(2, doc.getDocumentName());
        verify(ps).setString(3, doc.getDescription());
        verify(ps).executeUpdate();
        verify(ps).close();

//        assertDocumentIsStored();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenDocumnetIsNullExceptionOccurs() throws Exception {
        // given
        // there is no documents in database
        DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(conn);
        dao.addDocument(null);
    }

    @After
    public void destroy() {
        reset(rs);
    }

}
