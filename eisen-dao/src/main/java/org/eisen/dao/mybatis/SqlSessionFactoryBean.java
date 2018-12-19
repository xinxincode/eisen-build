package org.eisen.dao.mybatis;

import com.google.gson.Gson;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.eisen.dao.datasource.DefaultDataSourceFactory;
import org.eisen.dao.orm.mapper.TbFileDetailMapper;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

public class SqlSessionFactoryBean {
    public static void main(String[] args) {

    }

    public SqlSessionFactory newSqlSessionFactory() throws IOException {
        DataSource dataSource = DefaultDataSourceFactory.getHikariDataSource("db/ds2.properties", "hikari");

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMappers("**/*Mapper.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.commit();
        List list = sqlSession.selectList("org.eisen.dao.orm.mapper.TbFileDetailMapper.selectAll");
        System.out.println(new Gson().toJson(list));
        TbFileDetailMapper mapper = sqlSession.getMapper(TbFileDetailMapper.class);
        System.out.println(new Gson().toJson(mapper.selectAll()));
        return null;
    }

}
