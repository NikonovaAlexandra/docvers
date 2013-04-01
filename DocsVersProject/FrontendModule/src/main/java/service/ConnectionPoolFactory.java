package service;

import exception.BusinessException;
import exception.DAOException;
import exception.SystemException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
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
    private static int initialConnections;
    private static int maxConnections;
    private ConnectionPoolFactory() throws SQLException, IOException {
        Properties resource = new Properties();
        resource.load(new FileInputStream(Config.getDbPropertiesPath()));
        String url = resource.getProperty("url");
        String driver = resource.getProperty("driver");
        String user = resource.getProperty("user");
        String pass = resource.getProperty("password");
        initialConnections = Integer.parseInt(resource.getProperty("initialConnections"));
        if (initialConnections == 0) initialConnections = 1;
        maxConnections = Integer.parseInt(resource.getProperty("maxConnections"));
        if (maxConnections == 0 || maxConnections < initialConnections) maxConnections = initialConnections + 1;
        connectionPool =
                new ConnectionPool(driver, url, user, pass,
                        initialConnections,
                        maxConnections,
                        true);

    }
    public static ConnectionPoolFactory getInstance() {
        return instance;
    }

    public static void init() throws SQLException, IOException {
        if (instance == null) {
            instance = new ConnectionPoolFactory();
        }
    }
    public ConnectionPool getConnectionPool() throws SystemException, BusinessException {
            return connectionPool;
    }

    public static int getMaxConnections() {
        return maxConnections;
    }

    public static void setMaxConnections(int maxConnections) {
        ConnectionPoolFactory.maxConnections = maxConnections;
    }

    public static int getInitialConnections() {

        return initialConnections;
    }

    public static void setInitialConnections(int initialConnections) {
        ConnectionPoolFactory.initialConnections = initialConnections;
    }


}
