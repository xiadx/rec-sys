package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcDemoIII {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root123456");
            String sql = "insert into user (id,name,age,time) values (?,?,?,?)";
            ps = conn.prepareStatement(sql);
//            ps.setInt(1, 1001);
//            ps.setString(2, "xiadingxin");
//            ps.setInt(3, 18);
//            ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
//            System.out.println("insert a record");
//            int count = ps.executeUpdate();
//            System.out.println(count);
            ps.setObject(1, 1002);
            ps.setObject(2, "wanglei");
            ps.setObject(3, 20);
            ps.setObject(4, new java.sql.Date(System.currentTimeMillis()));
            System.out.println("insert a record");
            ps.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ps) {
                    ps.close();
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
