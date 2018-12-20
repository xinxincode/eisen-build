package org.eisen.dao.config.mybatis.mapperscan;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@MapperScan(value = "org.eisen.dao.orm.db2.mapper", sqlSessionTemplateRef = "hikari2")
public class MapperScan2 extends MapperScanBase {

    @Bean(value = "hikari2", name = "hikari2")
    SqlSessionTemplate getSqlSessionTemplate(@Value("hikari2") String id, @Value("db/ds2.properties") String path, @Value("hikari") String prefix) throws IOException {
        return new SqlSessionTemplate(getDataSourceFactory(id, path, prefix, HikariDataSource.class), ExecutorType.REUSE);
    }


}
