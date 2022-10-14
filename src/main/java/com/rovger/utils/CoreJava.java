package com.rovger.utils;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: TODO
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年04月19日 09:18
 */
public class CoreJava {

    public static void main(String[] args) {
//        final Pattern sqlPattern = Pattern.compile("select|update|delete|insert|trancate|drop|where|from|and|or|case|when|union",Pattern.CASE_INSENSITIVE);
//        System.out.println(sqlPattern.matcher("Weijie SELECT").replaceAll(""));
        final String sqlInjectRegex = "\\s+(insert|select|delete|update|from|where|count|grant|alter|truncate|drop|union|declare|exec|limit|case|when|sleep|like)\\s*";
        String mockStr = "1661845430362265879') OR (SELECT*FROM(SELECT(SLEEP(4)))insd) limit 1#\t";
        Pattern pattern = Pattern.compile(sqlInjectRegex);
        Matcher matcher = pattern.matcher(mockStr);
        System.out.println("======= " + matcher.find());
/*
        String str = null;
        Optional.ofNullable("weijie").orElse(str.toString());
*/

 /*
        long num1 = 0L;
        Long num2 = null;
        num1 = num2; // Long为null 赋给long 会报NullPointerException
*/
        byte a = 127;
        byte b = 127;
        // 因为 a+b 操作会将 a、b 提升为 int 类型，所以将 int 类型赋值给 byte 就会编译出错
//        b = a + b;
        // += 隐式的将加操作的结果类型强制转换为持有结果的类型。如果两这个整型相加，如 byte、short 或者 int，首先会将它们提升到 int 类型，然后在执行加法操作
        b += a;

        // 浮点数不能完全精确的表示出来
        System.out.println(3*0.1);

        /**
         * << :左移运算符,x << 1,相当于x乘以2(不溢出的情况下),低位补0
         * >> :带符号右移,x >> 1,相当于x除以2,正数高位补0,负数高位补1
         * >>> :无符号右移,忽略符号位,空位都以0补齐
         */
        System.out.println(8 << 1);

        // HashMap数组初始容量
        System.out.println("HashMap default initial capacity: " + (1 << 4));
        System.out.println("weijie".hashCode());
        System.out.println("Key index: " + ("weijie".hashCode() & "weijie".length()-1));

        System.out.println(add(2,3.4));

        System.out.println(convert(Lists.newArrayList(123)));
    }

    /**
     * Java泛型使用 --- 泛型上线
     * A extend B 表示A是B的子类或者子孙，在B下面。
     * @param a
     * @param b
     * @param <T>：要求此泛型必须是数字类型
     * @return
     */
    private static <T extends Number> double add(T a, T b) {

        List<? extends Number> nums = new ArrayList<>();
        // 若出现多种子类型，则会强制转换失败）
//        nums.add(4.2); // 编译错误
//        nums.add(4); // 编译错误

        return a.doubleValue() + b.doubleValue();
    }

    /**
     * 泛型下线 -- 只能接收String或Object类型的泛型，String类的父类只有Object类
     * A super B 表示A是B的父类或者祖先，在B的上面。
     * @return
     */
    private static String convert(List<? super String> list) {
        List<? super Number> nums = new ArrayList<>();
        nums.add(3.0);

        return null;
    }
}
