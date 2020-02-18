package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcDemoI {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            long start = System.currentTimeMillis();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root123456");
            long end = System.currentTimeMillis();
            System.out.println(conn);
            System.out.println("establishing a connection takes " + (end - start) + " milliseconds");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != conn) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
