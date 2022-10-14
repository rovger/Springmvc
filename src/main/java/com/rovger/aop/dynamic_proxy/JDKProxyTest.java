package com.rovger.aop.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Description: 上层调用端
 * @Author weijlu
 * @Date 2018/8/1 16:52
 */
public class JDKProxyTest {

    public static void main(String[] args) throws Exception {
        GamePlayer player = new GamePlayer("weijlu");
//        InvocationHandler handler = new GamePlayHandler(player);
//        ClassLoader classLoader = player.getClass().getClassLoader();
        IGamePlayer proxy = (IGamePlayer) Proxy.newProxyInstance(
                GamePlayHandler.class.getClassLoader(),
                player.getClass().getInterfaces(),
                (o, method, args1) -> {
                    Object invoke = method.invoke(player, args1);
                    return invoke;
                }
        );
        proxy.login("weijlu", "a111111");
        Thread.sleep(500);
        proxy.killBoss();
        Thread.sleep(500);
        proxy.upgrade();

        //测试反射获取类实例
        Class clazz = JDKProxyTest.class;
        System.out.println("clazz.getName():" + clazz.getName() + ", clazz.getSimpleName():" + clazz.getSimpleName());

        // 通过全限定名，创建对象
        JDKProxyTest client = (JDKProxyTest) Class.forName(clazz.getName()).newInstance();

        Class c = clazz.getClass();
        System.out.println("c.getName():" + c.getName());
    }
}
