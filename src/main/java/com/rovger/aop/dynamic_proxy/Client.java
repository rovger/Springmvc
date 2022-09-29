package com.rovger.aop.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 上层调用端
 * @Author weijlu
 * @Date 2018/8/1 16:52
 */
public class Client {

    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public static void main(String[] args) throws Exception {
        /*GamePlayer player = new GamePlayer("weijlu");
        InvocationHandler handler = new GamePlayHandler(player);
        System.out.println("开始时间是："+ format.format(new Date()));
        ClassLoader classLoader = player.getClass().getClassLoader();
        IGamePlayer proxy = (IGamePlayer) Proxy.newProxyInstance(classLoader, player.getClass().getInterfaces(), handler);
        proxy.login("weijlu", "a111111");
        Thread.sleep(500);
        proxy.killBoss();
        Thread.sleep(500);
        proxy.upgrade();
        System.out.println("结束时间是："+ format.format(new Date()));*/

        //测试反射获取类实例
        Class clazz = Client.class;
        System.out.println("clazz.getName():" + clazz.getName() + ", clazz.getSimpleName():" + clazz.getSimpleName());
        Client client = (Client) Class.forName(clazz.getName()).newInstance();

        Class c = clazz.getClass();
        System.out.println("c.getName():" + c.getName());
    }
}
