package org.eisen.dal.configuration;

import org.eisen.bios.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @Author Eisen
 * @Date 2018/12/21 20:02
 * @Description: 应用初始化操作
 **/
@org.springframework.context.annotation.Configuration
public class ApplicationInit {

    //设置时区静态代码块
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8:00"));
    }

    @Autowired
    Environment environment;

    @PostConstruct
    public void initMybatis() throws Exception {
        String em = environment.getProperty("eisen.mybatis");
        if (StringUtils.isEmpty(em)) {
            System.err.println("未指定datasource mybatis未初始化");
            return;
        }
        MybatisInit.initMybatis(em);
    }


}
