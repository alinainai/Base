package com.gas.test.learn.delegate;

import com.gas.test.utils.TUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Process {

    public static void main(String[] args) {

        //加入这一段可以在磁盘中生成 代理类，让我们看到代理类的真面目
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
//        IProxy proxy = (IProxy) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IProxy.class}, new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                System.out.println("Entered " + proxy.getClass().getName() + "-" + method.getName() + ",with arguments{" + args[0] + "}");
//                if(method.getName().equals("getString"))
//
//                return method.invoke(proxy, args);
//            }
//        });
//        proxy.getString("test");
    }

}
