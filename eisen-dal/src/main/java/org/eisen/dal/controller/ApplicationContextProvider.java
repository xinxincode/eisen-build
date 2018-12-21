package org.eisen.dal.controller;

import org.apache.ibatis.binding.MapperRegistry;
import org.eisen.dal.config.mybatis.mapperscan.MapperScanBase;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Configuration
public class ApplicationContextProvider implements ApplicationContextAware, ApplicationListener {

    public static ApplicationContext applicationContext;
    public static DefaultListableBeanFactory dlbf;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextProvider.applicationContext = applicationContext;
    }


    @PostConstruct
    public void registerSingletonMapper() throws Exception {
         dlbf = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();

        SqlSessionTemplate sst1 = MapperScanBase.getSqlSessionTemplate("db/ds1.properties", "hikari", "org.eisen.dao.orm.db1.mapper");
        addMapper(sst1);
        SqlSessionTemplate sst2 = MapperScanBase.getSqlSessionTemplate("db/ds2.properties", "hikari", "org.eisen.dao.orm.db2.mapper");
        addMapper(sst2);


    }


    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
    }

    public void addMapper(SqlSessionTemplate sst) {
        MapperRegistry mr = sst.getSqlSessionFactory().getConfiguration().getMapperRegistry();
        Collection<Class<?>> cc = mr.getMappers();
        if (cc != null) {
            for (Class<?> clz : cc) {
                dlbf.registerSingleton(clz.getName(), sst.getMapper(clz));
            }
        }
    }

}
