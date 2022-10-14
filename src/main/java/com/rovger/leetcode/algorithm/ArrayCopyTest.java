package com.rovger.leetcode.algorithm;

import com.google.common.collect.Lists;

import java.util.Arrays;

/**
 * @Description: ArrayList.remove()的实现
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年10月14日 17:01
 */
public class ArrayCopyTest {

    static String[] strArr = new String[]{"123", "456", "789", "weijie", "luwiejie", "LWJ"};
    static String[] rstArr = null;

    public static void main(String[] args) {
        System.out.println("元素删除前：" + Lists.newArrayList(strArr));
        String str = "weijie";
        removeObj(str, strArr);
        System.out.println("元素删除后：" + Arrays.toString(rstArr));
    }

    /**
     * 实现从一个数组中移除一个元素
     *
     * @param str
     * @param strArr
     */
    private static boolean removeObj(String str, String[] strArr) {
        if (strArr == null || strArr.length == 0) return false;
        if (str == null) {
            throw new RuntimeException("...");
        }
        int len = strArr.length;
        for (int i = 0; i < len; i++) {
            if (str.equals(strArr[i])) {
                // 删除元素后，待迁移元素数
                int numMoved = len - i - 1;
                // 将数组中删除元素以后的位置，copy到新数组被删除元素位置上
                System.arraycopy(strArr, i + 1, strArr, i, numMoved); // [123, 456, 789, luwiejie, LWJ, LWJ]

                // 为了这样 [123, 456, 789, luwiejie, LWJ]
                rstArr = new String[len-1];
                System.arraycopy(strArr, 0, rstArr, 0, len - 1);
                return true;
            }
        }
        return false;
    }
}
