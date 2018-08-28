package com.rovger.demo.reflect;

/**
 * @Description: 用于测试单利模式下通过反射获取Singleton实例
 * @Author weijlu
 * @Date 2018/7/31 14:54
 */
public class Singleton {

    private static Singleton instance = null;

    private Singleton () {

    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

}
