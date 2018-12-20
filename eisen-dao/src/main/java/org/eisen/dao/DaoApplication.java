package org.eisen.dao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

import java.util.TimeZone;

/**
 * @Author Eisen
 * @Date 2018/12/12 21:05
 * @Description:
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@PropertySource(value = {"classpath:application-dao.properties"})
public class DaoApplication {
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8:00"));
    }

    public static void main(String[] args) {
        SpringApplication.run(DaoApplication.class, args);
    }
}
