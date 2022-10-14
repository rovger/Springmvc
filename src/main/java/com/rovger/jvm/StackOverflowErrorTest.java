package com.rovger.jvm;

/**
 * @Description: TODO
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2021年12月22日 14:27
 */
public class StackOverflowErrorTest {

    private static int num = 0;
    private static void test() {
        System.out.println(num++);
        test();
    }
    public static void main(String[] args) {
        test();
    }

    /*private static long count = 0;

    private static void test() {
        System.out.println(count++);
        test();
    }

    public static void main(String[] args) {
        test();
    }

     */
}
