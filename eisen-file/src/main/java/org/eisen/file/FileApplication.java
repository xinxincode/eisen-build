package org.eisen.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author Eisen
 * @Date 2018/12/12 20:26
 * @Description:
 * //@PropertySource(value={"file:${user.dir}/config/customize.properties"})
 **/
@SpringBootApplication
@PropertySource(value={"classpath:application-file.properties"})
public class FileApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }
}
