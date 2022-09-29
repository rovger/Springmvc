package com.rovger.aop.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description: 动态代理类
 * @Author weijlu
 * @Date 2018/8/1 16:27
 */
public class GamePlayHandler implements InvocationHandler {

    Class clazz = null;//被代理者
    Object obj = null;//被代理的实例

    //我要代理谁
    public GamePlayHandler(Object _obj) {
        this.obj = _obj;
    }

    //调用被代理的方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(this.obj, args);
        return result;
    }
}
