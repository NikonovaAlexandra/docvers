package util;

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
public class ConnectionGetter {

    public static Connection getConnection() throws SQLException {
        ResourceBundle resource =
                ResourceBundle.getBundle("database");
        String url = resource.getString("url");
        String driver = resource.getString("driver");
        String user = resource.getString("user");
        String pass = resource.getString("password");
        try {
            Class.forName(driver).newInstance();
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver isn't loaded!");
        } catch (InstantiationException e) {e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, user, pass);
    }
}
