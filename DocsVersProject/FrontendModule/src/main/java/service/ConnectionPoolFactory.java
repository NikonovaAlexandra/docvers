package service;

import exception.BusinessException;
import exception.DAOException;
import exception.SystemException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 14.02.13
 * Time: 12:32
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionPoolFactory {
    private static ConnectionPoolFactory instance;
    private ConnectionPool connectionPool;
    public static synchronized ConnectionPoolFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionPoolFactory();
        }
        return instance;
    }

    public ConnectionPool getConnectionPool() throws SystemException, BusinessException {
        try {
        ResourceBundle resource =
                ResourceBundle.getBundle("database");
        String url = resource.getString("url");
        String driver = resource.getString("driver");
        String user = resource.getString("user");
        String pass = resource.getString("password");
        connectionPool =
                new ConnectionPool(driver, url, user, pass,
                        initialConnections(),
                        maxConnections(),
                        true);

        return connectionPool;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
        protected int initialConnections() {
            return(5);
        }
        /** Override this in subclass to change maximum number of
         *  connections.
         */
        protected int maxConnections() {
            return(50);
        }

}
