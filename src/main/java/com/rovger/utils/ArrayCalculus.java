package com.rovger.utils;

import java.util.Arrays;

/**
 * 描述：
 *
 * @author weijie.lu
 * @create 2019-08-21
 */
public class ArrayCalculus {

    public static void main(String[] args) {

        String[] arr_1 = new String[]{"1", "2", "3", "4"};
        String[] arr_2 = {"5", "6", "7"};

        // 定义结果集数组，初始化数组长度
        String[] resultArr = new String[arr_1.length + arr_2.length];

        System.arraycopy(arr_1, 0, resultArr, 0, arr_1.length);
        System.arraycopy(arr_2, 0, resultArr, arr_1.length, arr_2.length);

        System.out.println("arr_1: " + Arrays.toString(arr_1));
        System.out.println("arr_2: " + Arrays.toString(arr_2));
        System.out.println("resultArr: " + Arrays.toString(resultArr));
    }

}
