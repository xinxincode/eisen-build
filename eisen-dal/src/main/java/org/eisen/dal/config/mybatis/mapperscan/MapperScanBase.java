package org.eisen.dal.config.mybatis.mapperscan;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.eisen.dal.datasource.DataSourceFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Hashtable;

public abstract class MapperScanBase {
    public static Hashtable<String, SqlSessionFactory> dataSourceFactoryManagerMap = new Hashtable<String, SqlSessionFactory>();
    public static Hashtable<String, TransactionFactory> transactionFactoryManagerMap = new Hashtable<String, TransactionFactory>();

    public static SqlSessionFactory getDataSourceFactory(String id, String path, String prefix, Class clz, String... packageNames) throws IOException {
        DataSource ds = DataSourceFactory.getDataSource(path, prefix, clz);
        SpringManagedTransactionFactory tf = new SpringManagedTransactionFactory();
        Environment environment = new Environment(id, tf, ds);
        Configuration conf = new Configuration(environment);
        if (packageNames != null) {
            for (String packageName : packageNames) {
                conf.addMappers(packageName);
            }
        }
        SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(conf);
        dataSourceFactoryManagerMap.put("id", ssf);
        transactionFactoryManagerMap.put("id", tf);
        return ssf;
    }

    public static SqlSessionTemplate getSqlSessionTemplate(String path, String prefix, String... packageNames) throws Exception {
        String id = path.replace("/", ".").replace("\\", ".");
        if (!id.endsWith(".")) {
            id += ".";
        }
        if (id.startsWith(".")) {
            id = id.substring(1);
        }
        id += prefix;
        return new SqlSessionTemplate(getDataSourceFactory(id, path, prefix, HikariDataSource.class, packageNames), ExecutorType.REUSE);

    }


}
