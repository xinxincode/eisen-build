package org.eisen.dal.mybatis;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.eisen.dal.datasource.DataSourceFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Hashtable;

@Configuration
public class SqlSessionConfig {

    public static Hashtable<String, SqlSessionFactory> dataSourceFactoryManagerMap = new Hashtable<String, SqlSessionFactory>();

    public static SqlSessionFactory getDataSourceFactory(String id, String path, String prefix) throws IOException {
        DataSource dataSource = DataSourceFactory.getHikariDataSource(path, prefix);
        SpringManagedTransactionFactory transactionFactory = new SpringManagedTransactionFactory();
        Environment environment = new Environment(id, transactionFactory, dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(environment);
        SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(configuration);
        dataSourceFactoryManagerMap.put("id", ssf);
        return ssf;
    }

    public SqlSessionTemplate getSqlSessionTemplate() throws IOException {
        SqlSessionFactory ssf1 = getDataSourceFactory("ssf1", "db/ds1.properties", "hikari");
        SqlSessionTemplate sst1 = new SqlSessionTemplate(ssf1, ExecutorType.SIMPLE);
//        sst1.getMapper();
        sst1.flushStatements();
        return sst1;
    }

}
