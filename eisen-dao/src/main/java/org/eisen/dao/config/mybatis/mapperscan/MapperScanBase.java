package org.eisen.dao.config.mybatis.mapperscan;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.eisen.dao.datasource.DataSourceFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Hashtable;

public abstract class MapperScanBase {
    public static Hashtable<String, SqlSessionFactory> dataSourceFactoryManagerMap = new Hashtable<String, SqlSessionFactory>();
    public static Hashtable<String, TransactionFactory> transactionFactoryManagerMap = new Hashtable<String, TransactionFactory>();

    public static SqlSessionFactory getDataSourceFactory(String id, String path, String prefix, Class clz) throws IOException {
        DataSource ds = DataSourceFactory.getDataSource(path, prefix, clz);
        SpringManagedTransactionFactory tf = new SpringManagedTransactionFactory();
        Environment environment = new Environment(id, tf, ds);
        Configuration conf = new Configuration(environment);
        SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(conf);
        dataSourceFactoryManagerMap.put("id", ssf);
        transactionFactoryManagerMap.put("id", tf);
        return ssf;
    }

    abstract SqlSessionTemplate getSqlSessionTemplate(String id, String path, String prefix) throws Exception;


}
