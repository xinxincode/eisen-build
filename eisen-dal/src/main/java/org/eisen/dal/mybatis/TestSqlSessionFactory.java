//package org.eisen.dao.mybatis;
//
//import com.google.gson.Gson;
//import com.zaxxer.hikari.HikariDataSource;
//import org.apache.ibatis.mapping.Environment;
//import org.apache.ibatis.session.Configuration;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.apache.ibatis.transaction.TransactionFactory;
//import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
//import org.eisen.dao.datasource.DataSourceFactory;
//import org.eisen.dao.orm.db1.mapper.TbFileDetailMapper;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//import java.util.Collection;
//import java.util.List;
//
//public class TestSqlSessionFactory {
//    public static void main(String[] args) throws IOException {
//        newSqlSessionFactory();
//    }
//
//    public static SqlSessionFactory newSqlSessionFactory() throws IOException {
//        DataSource dataSource = DataSourceFactory.getDataSource("db/ds1.properties", "hikari", HikariDataSource.class);
//        TransactionFactory transactionFactory = new JdbcTransactionFactory();
//        Environment environment = new Environment("development", transactionFactory, dataSource);
//        Configuration configuration = new Configuration(environment);
//        configuration.addMappers("org.eisen.dao.orm");
////        configuration.addMappers("org.eisen.dao.orm.db2.mapper");
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        Collection collection = sqlSession.getConfiguration().getMapperRegistry().getMappers();
//        for (Object obj : collection) {
//            System.out.println(obj.getClass().getName());
//        }
//        sqlSession.commit();
//
//
//        TbFileDetailMapper mapper = sqlSession.getMapper(TbFileDetailMapper.class);
//        System.out.println(new Gson().toJson(mapper.selectAll()));
//
//        List list = sqlSession.selectList("org.eisen.dao.orm.db2.mapper.TbFileDetailMapper1.selectAll");
//        System.out.println(new Gson().toJson(list));
//        return null;
//    }
//
//}
