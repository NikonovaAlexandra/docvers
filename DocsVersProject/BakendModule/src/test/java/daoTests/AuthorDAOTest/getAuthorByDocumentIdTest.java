package daoTests.AuthorDAOTest;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 11.02.13
 * Time: 8:52
 * To change this template use File | Settings | File Templates.
 */
public class getAuthorByDocumentIdTest {

//    @Test
//    public void getAuthorByDocumentIdSuccessful() throws Exception {
//        Connection conn = mock(Connection.class);
//        PreparedStatement ps = mock(PreparedStatement.class);
//        ResultSet rs = mock(ResultSet.class);
//        Document doc = EntitiesFactory.createNewDocument();
//        long id = doc.getId();
//        when(conn.prepareStatement(anyString())).thenReturn(ps);
//        when(ps.executeQuery()).thenReturn(rs);
//        // there is no documents in database
//        AuthorDAO dao = DAOFactory.getInstance().getAuthorDAO(conn);
//        // when
//        dao.getAuthorByDocumentID(id);
//        // then
//        verify(conn).prepareStatement(QueriesSQL.SELECT_AUTHOR_ID_FROM_DOCUMENT_WHERE_ID);
//        verify(ps).setLong(1, id);
//        verify(ps).executeQuery();
//        verify(rs).next();
//        verify(ps).close();
//        verify(rs).close();
//    }
}
