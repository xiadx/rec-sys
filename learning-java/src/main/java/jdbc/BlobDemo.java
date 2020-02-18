package jdbc;

import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlobDemo {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream is = null;
        OutputStream os = null;
        String path = BlobDemo.class.getClassLoader().getResource("images/book").getPath();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root123456");

            ps = conn.prepareStatement("insert into user (id,name,age,img) values (?,?,?,?)");
            ps.setInt(1, 1001);
            ps.setString(2, "xiadingxin");
            ps.setInt(3, 18);
            ps.setBlob(4, new FileInputStream(path + "/prml.png"));
            ps.execute();

            ps = conn.prepareStatement("select * from user where id=?");
            ps.setObject(1, 1001);

            rs = ps.executeQuery();
            while (rs.next()) {
                Blob b = rs.getBlob("img");
                is = b.getBinaryStream();
                os = new FileOutputStream(path + "/prml-blob.png");
                int l = 0;
                while ((l = is.read()) != -1) {
                    os.write(l);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
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
