package com.rovger.leetcode;

/**
 * @Description: 整数反转
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年04月24日 14:00
 */
public class NumberReverse {

    public static void main(String[] args) {
        int num = -3453;
        System.out.println(reverse(num));
    }

    /**
     * 迭代法
     * @param num
     * @return
     */
    private static int reverse(int num) {

        int carry = 0;
        while (num != 0) {
            carry = carry * 10 + num % 10;
            num /= 10;
        }
        return carry;
    }
}
