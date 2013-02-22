package util;

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
public class ConnectionFactory {
    private static ConnectionFactory instance;

    public static synchronized ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() throws SystemException, BusinessException {
        ResourceBundle resource =
                ResourceBundle.getBundle("database");
        String url = resource.getString("url");
        String driver = resource.getString("driver");
        String user = resource.getString("user");
        String pass = resource.getString("password");
        try {
            Class.forName(driver).newInstance();
            return DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            throw new SystemException("Driver was not found!", e);
        } catch (InstantiationException e) {
            throw new SystemException("Specified class object cannot be instantiated" +
                    " because it is an interface or is an abstract class", e);
        } catch (IllegalAccessException e) {
            throw new SystemException("Currently executing method does not have access" +
                    " to the definition of the specified class, field, method or constructor", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


}
