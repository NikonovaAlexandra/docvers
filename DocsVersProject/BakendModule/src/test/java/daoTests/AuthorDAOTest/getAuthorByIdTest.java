package daoTests.AuthorDAOTest;

import dao.DAOFactory;
import dao.DAOType;
import dao.author.AuthorDAO;
import entities.Author;
import exception.NoSuchObjectInDB;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import service.QueriesHQL;
import service.QueriesSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 11.02.13
 * Time: 8:52
 * To change this template use File | Settings | File Templates.
 */
public class getAuthorByIdTest {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private Transaction tr;
    private Session session;
    private Query q;
    private Criteria criteria;

    @Before
    public void init () throws Exception {
        conn = mock(Connection.class);
        ps = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);
        session = mock(Session.class);
        tr = mock(Transaction.class);
        q = mock(Query.class);
        criteria = mock(Criteria.class);
        when(session.beginTransaction()).thenReturn(tr);
        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(session.createQuery(anyString())).thenReturn(q);
        when(q.setString("login", eq(anyString()))).thenReturn(q);
        when(session.createCriteria(Author.class)).thenReturn(criteria);
    }
    @Test
    public void getAuthorByDocumentIdSuccessful() throws Exception {

        when(rs.next()).thenReturn(true);
        AuthorDAO dao = DAOFactory.getInstance().getAuthorDAO(conn);
        // when
        long id = anyLong();
        dao.getAuthorByID(id);
        // then
        verify(conn).prepareStatement(QueriesSQL.SELECT_FROM_AUTHOR_WHERE_ID);
        verify(ps).setLong(1, id);
        verify(ps).executeQuery();
        verify(rs).next();
        verify(conn).commit();
        verify(ps).close();
        verify(rs).close();
    }

    @Test(expected = NoSuchObjectInDB.class)
    public void getAuthorByDocumentIdNoObject() throws Exception {
        when(rs.next()).thenReturn(false);
        AuthorDAO dao = DAOFactory.getInstance().getAuthorDAO(conn);
        // when
        dao.getAuthorByID(0);
        // then
        verify(conn).prepareStatement(QueriesSQL.SELECT_FROM_AUTHOR_WHERE_ID);
        verify(ps).setLong(1, 0);
        verify(ps).executeQuery();
        verify(rs).next();
        verify(conn).commit();
        verify(ps).close();
        verify(rs).close();
    }

    @Test
    public void getAuthorByIdSuccessfulHQL() throws Exception {

        AuthorDAO dao = DAOFactory.getInstance().getAuthorDAO(session, DAOType.HQL);
        // when
        dao.getAuthorByID(0);
        // then
        verify(session).beginTransaction();
        verify(session).createQuery(QueriesHQL.SELECT_FROM_AUTHOR_WHERE_ID);
        verify(q).setLong(eq("id"), anyLong());
        verify(q).uniqueResult();
        verify(tr).commit();
    }

    @Test
    public void getAuthorByIdSuccessfulCRITERIA() throws Exception {
        AuthorDAO dao = DAOFactory.getInstance().getAuthorDAO(session, DAOType.CRITERIA);
        // when
        dao.getAuthorByID(0);
        // then
        verify(session).beginTransaction();
        verify(session).createCriteria(Author.class);
        verify(criteria).add(Restrictions.eq("id", anyLong()));
        verify(criteria).uniqueResult();
        verify(tr).commit();
    }

    @After
    public void destroy() {
        reset(rs);
    }
}
