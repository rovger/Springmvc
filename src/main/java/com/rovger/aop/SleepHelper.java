package com.rovger.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by weijlu on 2017/5/9.
 * Spring创建代理会根据@Aspect注解找到切面
 */
@Aspect
public class SleepHelper {

    Logger logger = LoggerFactory.getLogger(SleepHelper.class);

    public SleepHelper() {}

    /**
     * 切点
     */
    @Pointcut("execution(* *.sleep())")
    public void sleeppoint() {
    }

    /**
     * 运行时通知
     * 声明前置通知：
     */
    @Before("sleeppoint()")
    public void beforeSleep() {
        logger.info("前置通知：觉前要脱衣服");
    }

    /**
     * 运行时通知
     * 声明后置通知：
     */
    @AfterReturning(pointcut = "sleeppoint()", returning = "result")
    public void afterSleep(String result) {
        logger.info("后置通知：睡醒了要穿衣服 " + result);
    }

    /**
     * 声明例外通知
     */
    @AfterThrowing(pointcut = "sleeppoint()", throwing = "ex")
    public void afterThrowing(Exception ex) {
        logger.info("例外通知：睡着睡着。。。床塌了。" + ex.getMessage());
    }

    /**
     * 最终通知
     */
    @After("sleeppoint()")
    public void after() {
        logger.info("最终通知");
    }

    /**
     * 环绕通知
     */
    @Around("sleeppoint()")
    public Object arround(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("进入方法-环绕通知");
        Object obj = pjp.proceed();
        logger.info("退出方法-环绕通知");
        return obj;
    }
}
