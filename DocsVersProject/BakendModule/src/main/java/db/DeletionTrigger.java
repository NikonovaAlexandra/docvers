package db;

import org.h2.api.Trigger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 28.03.13
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
public class DeletionTrigger implements Trigger {

    public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type) {
        // Initializing trigger
    }

    public void fire(Connection conn,
                     Object[] oldRow, Object[] newRow)
            throws SQLException {
//        BigDecimal diff = null;
//        if (newRow != null) {
//            diff = (BigDecimal) newRow[1];
//        }
//        if (oldRow != null) {
//            BigDecimal m = (BigDecimal) oldRow[1];
//            diff = diff == null ? m.negate() : diff.subtract(m);
//        }
        PreparedStatement prep = conn.prepareStatement(
                "UPDATE DOCUMENT SET DESCRIPTION=?");
//        prep.setBigDecimal(1, diff);
        prep.setString(1, "description");
        prep.execute();
    }
    public void close() {
        // the database is closed
    }

    public void remove() {
        // the trigger was dropped
    }
}
