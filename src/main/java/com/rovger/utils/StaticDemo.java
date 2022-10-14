package com.rovger.utils;

/**
 * @Description: TODO
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2021年07月23日 18:50
 */
public class StaticDemo {

    /**
     * 加不加final，静态代码块执行不一样
     */
    public static final String name = "weijie";
    private static long count = 0;

    static {
        System.out.println("static代码块...");
    }

    public static void main(String[] args) {
        for (;;) {
            System.out.println(count % 8);
            count++;
        }

//        System.out.println(6 % 5);
    }

}
