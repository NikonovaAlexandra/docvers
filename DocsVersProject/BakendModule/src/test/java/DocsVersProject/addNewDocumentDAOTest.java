package DocsVersProject;

import dao.DocumentDAO;
import dao.DocumentDAOImpl;
import entities.Document;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
    public void createNewDocumentSuccessful() throws Exception {
        Connection conn = Mockito.mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        // given

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        // there is no documents in database
        DocumentDAO dao = new DocumentDAOImpl(conn);
        // when
        Document doc = createNewDocument();
        dao.addDocument(doc);
        // then
        verify(conn).prepareStatement(DocumentDAOImpl.INSERT_INTO_DOCUMENT_ID_AUTHOR_NAME_DESCRIPTION_VALUES);
        verify(ps).setLong(1, doc.getId());
        verify(ps).setObject(2, doc.getAuthor());
        verify(ps).setString(3, doc.getName());
        verify(ps).setClob(4, doc.getDescription());
        verify(ps).executeUpdate();
        verify(ps).close();

//        assertDocumentIsStored();
    }

    @Test(expected=IllegalArgumentException.class)
    public void whenDocumnetIsNullExceptionOccurs() throws Exception {
        Connection conn = Mockito.mock(Connection.class);
        // given
        // there is no documents in database
        DocumentDAO dao = new DocumentDAOImpl(conn);
        dao.addDocument(null);
    }



    private void assertDocumentIsStored() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private Document createNewDocument() {
        return new Document();  //To change body of created methods use File | Settings | File Templates.
    }

}
