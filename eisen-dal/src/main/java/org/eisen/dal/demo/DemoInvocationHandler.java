package org.eisen.dal.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author Eisen
 * @Date 2018/12/20 23:24
 * @Description:
 **/
public class DemoInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return "success";
    }



}
