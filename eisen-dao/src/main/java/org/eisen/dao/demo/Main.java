package org.eisen.dao.demo;

import java.lang.reflect.Proxy;

/**
 * @Author Eisen
 * @Date 2018/12/20 23:27
 * @Description:
 **/
public class Main {
    public static void main(String[] args) {
        DemoInterface demoInterface = (DemoInterface) Proxy.newProxyInstance(DemoInterface.class.getClassLoader(), new Class[]{DemoInterface.class}, new DemoInvocationHandler());
        String s = demoInterface.say();
        System.out.println(s);
    }
}
