package org.eisen.dao.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ApplicationContextProvider implements ApplicationContextAware, ApplicationListener {

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextProvider.applicationContext = applicationContext;
    }


    @PostConstruct
    public void setB() {
        DefaultListableBeanFactory acbf = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        DaoConf d = new DaoConf();

        acbf.registerSingleton("org.eisen.dao.daoConf", d);

    }


    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
    }
}
