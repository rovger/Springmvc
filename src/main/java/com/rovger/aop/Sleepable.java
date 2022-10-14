package com.rovger.aop;

/**
 *
 * @author weijlu
 * @date 2017/5/9
 * Spring AOP的实现原理实际是“Java动态代理”，JDK的动态代理必须实现接口 -> CGLIB
 * <aop:aspectj-autoproxy proxy-target-class=false/>,该属性默认false，表示使用jdk动态创建代理，织入切面；为true时，表示使用cglib创建代理。
 */
public interface Sleepable {
    String sleep();
}
