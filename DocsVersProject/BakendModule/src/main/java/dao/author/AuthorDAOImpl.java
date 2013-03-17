package dao.author;

import entities.Author;
import exception.*;
import org.h2.constant.ErrorCode;
import service.QueriesSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 12.02.13
 * Time: 9:09
 * To change this template use File | Settings | File Templates.
 */
public class AuthorDAOImpl implements AuthorDAO {

    private Connection conn;

    public AuthorDAOImpl(Connection conn) throws DAOException, SystemException {
        if (conn == null)
            throw new NullConnectionException();
        try {
            this.conn = conn;
            this.conn.setAutoCommit(false);
        } catch (SQLException e) {
            if (e.getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                throw new NullConnectionException(e);
            if (e.getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                throw new NotEnoughRightsException(e);
            } else throw new DAOException(e);
        }

    }

    @Override
    public Author getAuthorByID(long id) throws DAOException, SystemException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(QueriesSQL.SELECT_FROM_AUTHOR_WHERE_ID);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            conn.commit();
            Author author = null;
            if (rs.next()) {
                author = new Author(rs.getLong("id"), rs.getString("login"), rs.getString("password"));
            } else throw new NoSuchObjectInDB("Author with id = " + id);
            return author;
        } catch (SQLException e) {
            if (e.getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                throw new NullConnectionException(e);
            if (e.getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                throw new NotEnoughRightsException(e);
            } else throw new DAOException(e);

        } finally {

            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    public Author getAuthorByLogin(String login) throws DAOException, SystemException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(QueriesSQL.SELECT_FROM_AUTHOR_WHERE_LOGIN);
            ps.setString(1, login);
            rs = ps.executeQuery();
            conn.commit();
            Author author = null;
            if (rs.next()) {
                author = new Author(rs.getLong("id"), rs.getString("login"), rs.getString("password"));
            } else throw new NoSuchObjectInDB("Author with login = " + login);
            return author;
        } catch (SQLException e) {
            if (e.getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                throw new NullConnectionException(e);
            if (e.getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                throw new NotEnoughRightsException(e);
            } else throw new DAOException(e);
        } finally {

            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

}
