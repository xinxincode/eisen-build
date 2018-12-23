package org.eisen.dal.configuration;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @Author Eisen
 * @Date 2018/12/21 20:33
 * @Description: ApplicationListener监听应用
 **/
@Configuration
public class ApplicationListenerProvider implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ContextClosedEvent) {
            System.out.println("应用关闭 执行关闭之前的操作");
        }
        if (applicationEvent instanceof ApplicationReadyEvent) {
            System.out.println("应用启动完毕 开始执行初始化操作");
        }
        if (applicationEvent instanceof ApplicationStartedEvent) {
            System.out.println("应用开始启动 请稍后......");
        }

//        System.out.println("onApplicationEvent : " + applicationEvent.toString());
    }
}
