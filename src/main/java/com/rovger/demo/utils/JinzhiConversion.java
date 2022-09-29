package com.rovger.demo.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 62进制转换
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2021年07月05日 17:41
 */
public class JinzhiConversion {

    private static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static String COMMAND_PREFIX = "Xm";
    private static int scale = 62;
    private static int minLength = 8;

    /**
     * 将数字转为62进制
     *
     * @param num
     * @return
     */
    public static String encode(long num) {
        StringBuilder sb = new StringBuilder(20);
        int remainder;
        while (num > scale - 1) {
            remainder = (int) (num % scale);
            sb.append(chars.charAt(remainder));

            num = num / scale;
        }

        sb.append(chars.charAt((int) num));
        String value = sb.reverse().toString();

        return StringUtils.leftPad(value, minLength, '0');
    }

    /**
     * 将62进制转为数字
     *
     * @param str
     * @return
     */
    public static long decode(String str) {
        str = str.replaceAll("^0*", "");
        long num = 0;
        int index;
        for (int i = 0; i < str.length(); i++) {
            // 查找字符的索引位置
            index = chars.indexOf(str.charAt(i));
            // 索引位置代表字符的数值
            num += index * Math.pow(scale, str.length() - i - 1);
        }
        return num;
    }

    public static void main(String[] args) {
//        System.out.println("62进制：" + encode(100L));
        System.out.println("10进制：" + decode("0000001c"));
    }
}
