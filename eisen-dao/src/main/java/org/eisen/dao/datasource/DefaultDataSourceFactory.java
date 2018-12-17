package org.eisen.dao.datasource;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @Author Eisen
 * @Date 2018/12/16 23:57
 * @Description:
 **/
public class DefaultDataSourceFactory implements DataSourceFactory {
    @Override
    public DataSource getDataSource() {
        try {
            return   DefaultDataSourceFactory.getHikariDataSource("db/ds1.properties", "hikari");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DataSource getDataSource(Class clz) {
        return null;
    }


    static HikariDataSource getHikariDataSource(Map<String, String> kv) {
        HikariDataSourceBuilder hdsb = new HikariDataSourceBuilder().
                url(kv.get("url")).
                driver(kv.get("driver")).
                username(kv.get("username")).
                password(kv.get("password")).
                poolname(kv.get("poolname"));

        if (kv.get("isAutoCommit") != null && !"".equals(kv.get("isAutoCommit"))) {
            hdsb.isAutoCommit(Boolean.valueOf(kv.get("isAutoCommit")));
        }
        if (kv.get("connectionTimeout") != null && !"".equals(kv.get("connectionTimeout"))) {
            hdsb.connectionTimeout(Long.valueOf(kv.get("connectionTimeout")));
        }
        if (kv.get("idleTimeout") != null && !"".equals(kv.get("idleTimeout"))) {
            hdsb.idleTimeout(Long.valueOf(kv.get("idleTimeout")));
        }
        if (kv.get("maxLifetime") != null && !"".equals(kv.get("maxLifetime"))) {
            hdsb.maxLifetime(Long.valueOf(kv.get("maxLifetime")));
        }
        if (kv.get("minIdle") != null && !"".equals(kv.get("minIdle"))) {
            hdsb.minIdle(Integer.valueOf(kv.get("minIdle")));
        }
        if (kv.get("maxPoolSize") != null && !"".equals(kv.get("maxPoolSize"))) {
            hdsb.maxPoolSize(Integer.valueOf(kv.get("maxPoolSize")));
        }
        if (kv.get("leakDetectionThreshold") != null && !"".equals(kv.get("leakDetectionThreshold"))) {
            hdsb.leakDetectionThreshold(Long.valueOf(kv.get("leakDetectionThreshold")));
        }

        Set<String> ss = kv.keySet();
        for (String s : ss) {
            if (s != null && s.startsWith("dataSourceProperties")) {
                hdsb.addDataSourceProperty(s.substring("dataSourceProperties".length() + 1), kv.get(s));
            }
            if (s != null && s.startsWith("healthCheckProperty")) {
                hdsb.addHealthCheckProperty(s.substring("healthCheckProperty".length() + 1), kv.get(s));
            }
        }
        return hdsb.build();
    }

    public static HikariDataSource getHikariDataSource(String path,String prefix) throws IOException {
        Properties props = new Properties();
        InputStream in = DefaultDataSourceFactory.class.getClassLoader().getResourceAsStream(path);
        props.load(in);
        Set<String> ss = props.stringPropertyNames();
        Map<String,String> map = new HashMap<String,String>();
        for (String s : ss) {
            if (s!=null && s.startsWith(prefix)) {
                map.put(s.substring(prefix.length() + 1), props.getProperty(s));
            }
        }
        return getHikariDataSource(map);
    }

    public static void main(String[] args) throws Exception {
        getHikariDataSource("db/ds1.properties", "hikari");
    }


}
