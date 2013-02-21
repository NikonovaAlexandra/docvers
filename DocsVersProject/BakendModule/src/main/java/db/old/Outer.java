package db.old;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 12.02.13
 * Time: 9:47
 * To change this template use File | Settings | File Templates.
 */
public class Outer {
    private static final Logger slf4jLogger = LoggerFactory.getLogger(Outer.class);
    public static void documentOut(ResultSet resultSet) throws SQLException {
        while(resultSet.next()){
            slf4jLogger.trace(resultSet.getLong("ID") + " " + resultSet.getString("document_name") + " " + resultSet.getString("author_id") + " " + resultSet.getString("description"));
        }
    }

    public static void versionOut(ResultSet resultSet) throws SQLException {
        while(resultSet.next()){
            slf4jLogger.trace(resultSet.getLong("ID") + " auth:" + resultSet.getString("author_id") + " doc:" + resultSet.getString("document_id") + " " + resultSet.getString("date"));
        }
    }

    public static void authorOut(ResultSet resultSet) throws SQLException {
        while(resultSet.next()){
            slf4jLogger.trace(resultSet.getLong("ID") + " " + resultSet.getString("login") + " " + resultSet.getString("password"));
        }
    }
}
