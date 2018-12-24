package org.eisen.dal.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.eisen.bios.utils.StringUtils;
import org.eisen.dal.configuration.DalException;
import org.eisen.dal.datasource.hikari.HikariDataSourceBuilder;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 连接池工厂
 */
public class DataSourceFactory {

    /**
     * 根据配置文件创建对应类型的连接池
     *
     * @param map 配置内容
     * @return
     */
    public static DataSource getDataSource(Map<String, String> map) {
        try {
            if (map == null || map.isEmpty()) {
                throw new Exception("连接配置为空");
            }
            String dataSourceClass = map.get("dataSourceClass");
            if (StringUtils.isEmpty(dataSourceClass)) {
                throw new DalException("dataSourceClass属性不能为空");
            }
            Class clz = Class.forName(dataSourceClass);
            Method[] methods = DataSourceFactory.class.getMethods();
            for (Method method : methods) {
                if (clz.equals(method.getReturnType())) {
                    return (DataSource) method.invoke(null, map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DalException(e);
        }
        throw new DalException("未找到指定连接池类型");
    }

    public static HikariDataSource getHikariDataSource(Map<String, String> map) {
        return HikariDataSourceBuilder.build(map);
    }

}