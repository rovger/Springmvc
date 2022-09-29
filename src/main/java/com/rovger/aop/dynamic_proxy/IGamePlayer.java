package com.rovger.aop.dynamic_proxy;

/**
 * @Description: 代理接口
 * @Author weijlu
 * @Date 2018/8/1 16:39
 */
public interface IGamePlayer {
    public void login(String user, String pwd);

    public void killBoss();

    public void upgrade();
}
