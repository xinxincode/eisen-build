package org.eisen.dal.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Eisen
 * @Date 2018/12/21 20:33
 * spring context 获取类
 * 提供spring容器两个操作类
 * ApplicationContext
 */
@Configuration
public class ApplicationContextProvider implements ApplicationContextAware {

    public static ApplicationContext applicationContext;
    public static DefaultListableBeanFactory defaultListableBeanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextProvider.applicationContext = applicationContext;
        defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
    }

}
