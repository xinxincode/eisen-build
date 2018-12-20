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
@MapperScan(value = "org.eisen.dao.orm.db1.mapper", sqlSessionTemplateRef = "hikari1")
public class MapperScan1 extends MapperScanBase {

    @Override
    @Bean(value = "hikari1", name = "hikari1")
    SqlSessionTemplate getSqlSessionTemplate(@Value("hikari1") String id, @Value("db/ds1.properties") String path, @Value("hikari") String prefix) throws IOException {
        return new SqlSessionTemplate(getDataSourceFactory(id, path, prefix, HikariDataSource.class), ExecutorType.REUSE);
    }


}
