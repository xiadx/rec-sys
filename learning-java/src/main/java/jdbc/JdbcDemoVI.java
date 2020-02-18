package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcDemoVI {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root123456");
            conn.setAutoCommit(false);
            ps1 = conn.prepareStatement("insert into user (id,name,age) values (?,?,?)");
            ps1.setObject(1, 1001);
            ps1.setObject(2, "xiadingxin");
            ps1.setObject(3, 18);
            ps1.execute();
            System.out.println("insert a user xiadingxin");
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ps2 = conn.prepareStatement("insert into user (id,name,age) values (?,?,?,?)");
            ps2.setObject(1, 1002);
            ps2.setObject(2, "wanglei");
            ps2.setObject(3, 20);
            ps2.execute();
            System.out.println("insert a user wanglei");
            conn.commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ps1) {
                    ps1.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (null != ps2) {
                    ps2.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
