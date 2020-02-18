package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcDemo {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            ps = conn.prepareStatement("insert into user (id,name,age) values (?,?,?)");
            ps.setInt(1, 1002);
            ps.setString(2, "wanglei");
            ps.setInt(3, 20);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(rs, ps, conn);
        }
    }

}
