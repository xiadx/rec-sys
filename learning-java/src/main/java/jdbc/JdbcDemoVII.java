package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class JdbcDemoVII {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root123456");
            for (int i = 0; i < 1000; i++) {
                ps = conn.prepareStatement("insert into user (id,name,beginTime,endTime) values (?,?,?,?)");
                ps.setObject(1, 1000 + i);
                ps.setObject(2, "abc" + (1000 + i));
                int rand = 100000000 + new Random().nextInt(1000000000);
                java.sql.Date date = new java.sql.Date(System.currentTimeMillis() - rand);
                java.sql.Timestamp stamp = new java.sql.Timestamp(System.currentTimeMillis() - rand);
                ps.setDate(3, date);
                ps.setTimestamp(4, stamp);
                ps.execute();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
