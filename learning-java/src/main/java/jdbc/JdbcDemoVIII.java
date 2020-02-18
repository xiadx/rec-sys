package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JdbcDemoVIII {

    public static long str2Date(String dateStr) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return format.parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root123456");
            ps = conn.prepareStatement("select * from user where endTime>? and endTime<? order by endTime");
            Timestamp start = new Timestamp(str2Date("2020-02-08 11:05:20"));
            Timestamp end = new Timestamp(str2Date("2020-02-12 11:05:20"));
            ps.setObject(1, start);
            ps.setObject(2, end);
            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("id --> " + rs.getInt("id") + ", name --> " + rs.getString("name") + ", endTime --> " + rs.getTimestamp("endTime"));
            }
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
