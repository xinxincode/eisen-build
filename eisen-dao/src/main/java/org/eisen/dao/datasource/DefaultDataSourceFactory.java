package org.eisen.dao.datasource;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @Author Eisen
 * @Date 2018/12/16 23:57
 * @Description:
 **/
public class DefaultDataSourceFactory implements DataSourceFactory {
    @Override
    public DataSource getDataSource() {
        return null;
    }

    @Override
    public DataSource getDataSource(Class clz) {
        return null;
    }

    public static HikariDataSource getHikariDataSource() {
        return new HikariDataSourceBuilder().
                url("jdbc:mysql://192.168.200.177:3306/eisendb").
                driver("com.mysql.cj.jdbc.Driver").
                username("eisen").
                password("eisen").
                poolname("p1").
                build();
    }

    public static void main(String[] args) throws Exception{
        DataSource ds = DefaultDataSourceFactory.getHikariDataSource();
        Connection conn = ds.getConnection();
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
