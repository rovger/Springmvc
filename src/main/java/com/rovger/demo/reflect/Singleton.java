package com.rovger.demo.reflect;

/**
 * @Description: 用于测试单利模式下通过反射获取Singleton实例
 * @Author weijlu
 * @Date 2018/7/31 14:54
 */
public class Singleton {

    public static volatile Singleton singleton;

    /**
     * 私有构造函数，拒绝外部访问
     */
    private Singleton() {}

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

}
