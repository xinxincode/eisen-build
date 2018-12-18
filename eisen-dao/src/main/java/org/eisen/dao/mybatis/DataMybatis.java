package org.eisen.dao.mybatis;

import com.google.gson.Gson;
import org.apache.ibatis.io.Resources;
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
import java.io.Reader;
import java.util.List;
import java.util.TimeZone;

/**
 * @Author Eisen
 * @Date 2018/12/15 20:12
 * @Description:
 **/
public class DataMybatis {
    public static void main(String[] args) throws Exception {
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.out.println(TimeZone.getDefault());
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        new DataMybatis().m();
    }

    public void my() throws IOException {
        Reader reader = Resources.getResourceAsReader("");
//        SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build();
    }

    public void m() throws IOException {
        DataSource dataSource = DefaultDataSourceFactory.getHikariDataSource("db/ds2.properties", "hikari");
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(TbFileDetailMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List list = sqlSession.selectList("org.eisen.dao.orm.mapper.TbFileDetailMapper.selectAll");
        System.out.println(new Gson().toJson(list));
        TbFileDetailMapper mapper = sqlSession.getMapper(TbFileDetailMapper.class);
        System.out.println(new Gson().toJson(mapper.selectAll()));
    }



}
