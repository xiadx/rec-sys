package jdbc;

import java.io.File;
import java.io.Reader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClobDemo {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Reader reader = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root123456");

            ps = conn.prepareStatement("insert into user (id,name,age,info) values (?,?,?,?)");
            ps.setInt(1, 1001);
            ps.setString(2, "xiadingxin");
            ps.setInt(3, 18);
			ps.setClob(4, new FileReader(new File("README.md")));
//            ps.setClob(4, new BufferedReader(new InputStreamReader(new ByteArrayInputStream("Recommendation System".getBytes()))));
            ps.execute();

            ps = conn.prepareStatement("select * from user where id=?");
            ps.setObject(1, 1001);

            rs = ps.executeQuery();
            while (rs.next()) {
                Clob c = rs.getClob("info");
                reader = c.getCharacterStream();
                int l = 0;
                while ((l = reader.read()) != -1) {
                    System.out.print((char)l);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
