package org.eisen.dao.jdbc;

import java.io.File;
import java.sql.*;

/**
 * @Author Eisen
 * @Date 2018/12/15 1:09
 * @Description:
 **/
public class DaoJDBC {

    public static void main(String[] args) throws Exception {
//        new DaoJDBC().jdbc();
        String s = DaoJDBC.class.getResource("/").getPath();
        String s1 = DaoJDBC.class.getResource("").getPath();
        System.out.println(s);
        System.out.println(s1);

        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        System.out.println(courseFile);

    }

    public void jdbc() throws SQLException {
//        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.200.177:3306/eisendb?" +
                "serverTimezone=GMT%2B8&autoReconnect=true&failOverReadOnly=false&autoReconnect=true&autoReconnectForPools=true&" +
                "user=eisen&password=eisen");
        Statement stat = conn.createStatement();
        stat.execute("select * from tb_file_detail");
        ResultSet rs = stat.getResultSet();
        int r = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= r; i++) {
                System.out.print(rs.getObject(i));
                System.out.print("     ");
            }
            System.out.println();
            System.out.println("======>>");
        }
        rs.close();
        stat.close();
        conn.close();
    }

}
