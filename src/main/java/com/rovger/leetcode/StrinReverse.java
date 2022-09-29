package com.rovger.leetcode;

/**
 * @Description: 字符串反转
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年04月22日 18:40
 */
public class StrinReverse {

    public static void main(String[] args) {
        String str = "123456";
        System.out.println(reverse2(str));
    }

    /**
     * 递归
     *
     * @param str
     * @return
     */
    private static String reverse(String str) {
        if (null == str || str.length() <= 1) {
            return str;
        }
        // str.substring(1) 截取从1~length()-1
        return reverse(str.substring(1)) + str.charAt(0);
    }

    /**
     * StrinBuffer
     *
     * @param str
     * @return
     */
    private static String reverse2(String str) {
        if (null == str || str.length() <= 1) {
            return str;
        }
        StringBuffer buffer = new StringBuffer(str.length());
        for (int i = str.length() - 1; i >= 0; i--) {
            buffer.append(str.charAt(i));
        }
        return buffer.toString();
    }

    /**
     * StrinBuffer
     *
     * @param str
     * @return
     */
    private static String reverse3(String str) {
        if (null == str || str.length() <= 1) {
            return str;
        }
        return new StringBuffer(str).reverse().toString();
    }
}
