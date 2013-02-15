package DocsVersProject.daoTests.VersionDAOTest;

import DocsVersProject.daoTests.EntitiesFactory;
import dao.document.DocumentDAO;
import dao.document.DocumentDAOImpl;
import dao.version.VersionDAO;
import dao.version.VersionDAOImpl;
import entities.Document;
import entities.Version;
import org.junit.Test;
import org.mockito.Mockito;
import util.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
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
        VersionDAO dao = new VersionDAOImpl(conn);
        // when
        Document doc = EntitiesFactory.createNewDocument();
        dao.getVersionsOfDocument(doc.getId());
        // then
        verify(conn).prepareStatement(Queries.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID);
        verify(ps).setLong(1, doc.getId());
        verify(ps).executeQuery();
        verify(rs).next();
        verify(ps).close();
        verify(rs).close();

    }

}
