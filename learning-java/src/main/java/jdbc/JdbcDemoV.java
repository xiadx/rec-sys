package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDemoV {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root123456");
            conn.setAutoCommit(false);
            long start = System.currentTimeMillis();
            stmt = conn.createStatement();
            for (int i = 0; i < 8000; i++) {
                stmt.addBatch("insert into user (id,name,age,time) values (" + (1999 + i) + ",'abc" + (1999 + i) + "',20,now())");
            }
            stmt.executeBatch();
            conn.commit();
            long end = System.currentTimeMillis();
            System.out.println("insert 8000 record takes " + (end - start) + " milliseconds");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (stmt != null) {
                    stmt.close();
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
