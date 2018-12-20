package org.eisen.dao.datasource.hikari;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @Author Eisen
 * @Date 2018/12/16 23:57
 * @Description:
 **/
public class HikariDataSourceFactory {
    public static DataSource getDataSource(String path, String prefix) {
        try {
            return HikariDataSourceBuilder.build(path, prefix);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
