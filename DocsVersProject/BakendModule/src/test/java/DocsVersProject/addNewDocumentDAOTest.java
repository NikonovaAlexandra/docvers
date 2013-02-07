package DocsVersProject;

import dao.DocumentDAO;
import dao.DocumentDAOImpl;
import entities.Document;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 07.02.13
 * Time: 10:56
 * To change this template use File | Settings | File Templates.
 */
public class addNewDocumentDAOTest {

    @Test
    public void createNewDocument1() {

    }
    @Test
    public void createNewDocumentSuccessful() throws Exception {
        Connection conn = Mockito.mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        // given
        doReturn(ps).when(conn).prepareStatement(anyString());
        // there is no documents in database
        DocumentDAO dao = new DocumentDAOImpl(conn);
        // when
        Document doc = createNewDocument();
        dao.addDocument(doc);
        // then
        verify(conn, only()).prepareStatement("insert into documents (id, name) values (?,?)");
        verify(ps).setLong(1, doc.getId());
        verify(ps).setString(2, doc.getName());
        verify(ps, only()).executeUpdate();
//        assertDocumentIsStored();
    }

    @Test(expected=IllegalArgumentException.class)
    public void whenDocumnetIsNullExceptionOccurs() throws Exception {
        Connection conn = Mockito.mock(Connection.class);
//        PreparedStatement ps = mock(PreparedStatement.class);
//        // given
//        doReturn(ps).when(conn).prepareStatement(anyString());
        // there is no documents in database
        DocumentDAO dao = new DocumentDAOImpl(conn);
        // when
        dao.addDocument(null);
        // then
//        verify(ps, only()).executeUpdate();
//        assertDocumentIsStored();
    }


    private void assertDocumentIsStored() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private Document createNewDocument() {
        return new Document();  //To change body of created methods use File | Settings | File Templates.
    }

}
