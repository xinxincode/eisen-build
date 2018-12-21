package org.eisen.dal.config.mybatis.mapperscan;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Collection;

//@Configuration
//@MapperScan(value = "org.eisen.dao.orm.db1.mapper", sqlSessionTemplateRef = "hikari1")
public class MapperScan1 extends MapperScanBase {

//    @Override
//    @Bean(value = "hikari1", name = "hikari1")
    public SqlSessionTemplate getSqlSessionTemplate(@Value("hikari1") String id, @Value("db/ds1.properties") String path, @Value("hikari") String prefix) throws IOException {
        return new SqlSessionTemplate(getDataSourceFactory(id, path, prefix, HikariDataSource.class), ExecutorType.REUSE);
    }

    public static void main(String[] args) throws IOException {
        SqlSessionTemplate sst = new MapperScan1().getSqlSessionTemplate("hikari1", "db/ds1.properties", "hikari");
        SqlSessionFactory ssf = sst.getSqlSessionFactory();
        org.apache.ibatis.session.Configuration cfg = ssf.getConfiguration();
        cfg.addMappers("org.eisen.dao.orm.db1.mapper");
        MapperRegistry mr = cfg.getMapperRegistry();

        Collection<Class<?>> cc = mr.getMappers();
        for (Class<?> clz : cc) {
            System.out.println(clz.getName());
        }



    }


}
