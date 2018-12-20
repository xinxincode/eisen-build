package org.eisen.dao.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.eisen.dao.datasource.hikari.HikariDataSourceBuilder;

import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 连接池工厂
 */
public class DataSourceFactory {

    /**
     * 根据配置文件创建对应类型的连接池
     * @param path 配置文件路径
     * @param prefix 配置文件内容数据库连接池前缀
     * @param clz 要创建的连接池类型
     * @return
     */
    public static DataSource getDataSource(String path, String prefix, Class clz) {
        try {
            if (path == null || path.equals("") || clz == null) {
                throw new Exception("连接配置为空");
            }
            Method[] methods = DataSourceFactory.class.getMethods();
            for (Method method : methods) {
                if (clz.equals(method.getReturnType())) {
                    return (DataSource) method.invoke(null, path, prefix);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }


    public static HikariDataSource getHikariDataSource(String path, String prefix) {
        try {
            return HikariDataSourceBuilder.build(path, prefix);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
