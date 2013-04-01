package dao.author;

import dao.ExceptionsThrower;
import entities.Author;
import exception.DAOException;
import exception.MyException;
import exception.NoSuchObjectInDB;
import exception.NullConnectionException;
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

    public AuthorDAOImpl(Connection conn) throws MyException {
        if (conn == null)
            throw new NullConnectionException();
        this.conn = conn;

    }

    @Override
    public Author getAuthorByID(long id) throws MyException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(QueriesSQL.SELECT_FROM_AUTHOR_WHERE_ID);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            Author author = null;
            if (rs.next()) {
                author = new Author(rs.getLong("id"), rs.getString("login"), rs.getString("password"));
            } else throw new NoSuchObjectInDB("Author with id = " + id);
            return author;
        } catch (SQLException e) {
            throw ExceptionsThrower.throwException(e);

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
    public Author getAuthorByLogin(String login) throws MyException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(QueriesSQL.SELECT_FROM_AUTHOR_WHERE_LOGIN);
            ps.setString(1, login);
            rs = ps.executeQuery();
            Author author = null;
            if (rs.next()) {
                author = new Author(rs.getLong("id"), rs.getString("login"), rs.getString("password"));
            } else throw new NoSuchObjectInDB("Author with login = " + login);
            return author;
        } catch (SQLException e) {
            throw ExceptionsThrower.throwException(e);
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
