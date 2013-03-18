package daoTests.DocumentDAOTest;

import exception.NoSuchObjectInDB;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 11.02.13
 * Time: 8:27
 * To change this template use File | Settings | File Templates.
 */
public class deleteDocumentDAOTest {

    @Test (expected = NoSuchObjectInDB.class)
    public void deleteDocumentSuccessful() throws Exception {
//        Connection conn = mock(Connection.class);
//        PreparedStatement ps = mock(PreparedStatement.class);
//        when(conn.prepareStatement(anyString())).thenReturn(ps);
//        // there is no documents in database
//        DocumentDAO dao = DAOFactory.getInstance().getDocumentDAO(conn);
//        // when
//        Document doc = EntitiesFactory.createNewDocument();
//        dao.deleteDocument(doc.getId());
//        // than
//        verify(conn).prepareStatement(QueriesSQL.DELETE_FROM_DOCUMENT_WHERE_ID);
//        verify(ps).setLong(1, doc.getId());
//        verify(ps).executeUpdate();
//        verify(ps).close();

    }

}
