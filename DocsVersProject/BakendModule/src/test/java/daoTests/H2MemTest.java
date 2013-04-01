package daoTests;

import org.h2.api.Trigger;

import java.sql.*;

public class H2MemTest {

    public static void main(String args[]) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:", "sa", "");
        Statement st = conn.createStatement();
        st.execute("create table customer(id integer, name varchar(30))");
        st.execute("CREATE TRIGGER INV_INS AFTER INSERT ON customer FOR EACH ROW CALL \""+H2MemTest.MyTrigger.class.getName()+"\"");
        st.execute("insert into customer values (1, 'Thomas')");


        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery("select name from customer");
        while (rset.next()) {
            String name = rset.getString(1);
            System.out.println(name);
        }

    }

    public static class MyTrigger implements Trigger {

        public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type) {
            // Initializing trigger
        }

        @Override
        public void close() throws SQLException {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void remove() throws SQLException {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public void fire(Connection conn,
                         Object[] oldRow, Object[] newRow)
                throws SQLException {
            PreparedStatement prep = conn.prepareStatement(
                    "update customer set name=? where id=?");
            prep.setString(1, (String) newRow[1] + "-modified");
            prep.setInt(2, (Integer) newRow[0]);
            prep.execute();
        }
    }
}