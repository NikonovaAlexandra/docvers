package DocsVersProject;

import dao.DocumentDAO;
import dao.DocumentDAOImpl;
import entities.Document;
import entities.Version;
import junit.framework.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 08.02.13
 * Time: 10:09
 * To change this template use File | Settings | File Templates.
 */
public class getVersionsOfDocumentDAOTest {

    @Test
    public void getVersionsOfDocumentSuccessful() throws Exception {
        Connection conn = Mockito.mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        Version version = mock(Version.class);
        // given
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        // there is no documents in database
        DocumentDAO dao = new DocumentDAOImpl(conn);
        // when
        Document doc = createNewDocument();
        dao.getVersionsOfDocument(doc);
        // then
        verify(conn).prepareStatement(DocumentDAOImpl.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID);
        verify(ps).setLong(1, doc.getId());
        verify(ps).executeQuery();
        verify(rs).next();
        verify(ps).close();
        verify(rs).close();

    }

    @Test(expected=IllegalArgumentException.class)
    public void whenDocumentIsNullExceptionOccurs() throws Exception {
        Connection conn = Mockito.mock(Connection.class);
//        // given
        // there is no documents in database
        DocumentDAO dao = new DocumentDAOImpl(conn);
        // when
        dao.getVersionsOfDocument(null);

    }
    private Document createNewDocument() {
        return new Document();  //To change body of created methods use File | Settings | File Templates.
    }
}
