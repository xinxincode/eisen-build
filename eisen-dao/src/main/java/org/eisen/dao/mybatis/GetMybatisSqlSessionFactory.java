package org.eisen.dao.mybatis;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.eisen.dao.datasource.DefaultDataSourceFactory;
import org.eisen.dao.orm.mapper.Mapper;
import org.eisen.dao.orm.mapper.TbFileDetailMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @Author Eisen
 * @Date 2018/12/15 20:12
 * @Description:
 **/
@org.springframework.context.annotation.Configuration
public class GetMybatisSqlSessionFactory {

    public SqlSessionFactory getDataSourceFactory(String id,String path, String prefix, String... packageNames) throws IOException {
        DataSource dataSource = DefaultDataSourceFactory.getHikariDataSource(path, prefix);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment(id, transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        for (String pn : packageNames) {
            configuration.addMappers(pn, Mapper.class);
        }
        return new SqlSessionFactoryBuilder().build(configuration);
    }

    @Bean(name = "sst1",value = "sst1")
    public SqlSessionTemplate getSqlSessionTemplate() throws IOException {
        SqlSessionFactory ssf1 = getDataSourceFactory("ssf1","db/ds2.properties", "hikari1", "org.eisen.dao.orm.mapper");
        SqlSessionTemplate sst1 = new SqlSessionTemplate(ssf1, ExecutorType.SIMPLE);
        return sst1;
    }

    public static void main(String[] args) throws IOException {
        TbFileDetailMapper m = new GetMybatisSqlSessionFactory().getSqlSessionTemplate().getMapper(TbFileDetailMapper.class);
        System.out.println(m.selectAll());
    }

}
