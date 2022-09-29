package com.rovger.leetcode;

/**
 * @Description: 回文
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年04月26日 13:31
 */
public class Palindrome {

    public static void main(String[] args) {
        // 判断回文数
        System.out.println(isPalindrome(1221));

        // 最长回文子串
//        System.out.println(longestPalindrome("fwefwew"));

        byte a = 2;
        System.out.println(a == 2);
    }

    /**
     * 获取最长回文子串：babad -> bab，ycbbcy -> cbbc
     * @param s
     * @return
     */
    private static String longestPalindrome(String s) {
        // 回文串记录
        String rst = "";



        return null;
    }

    /**
     * 判断回文数，负数不算回文数，-121 != 121
     * @param num
     * @return
     */
    private static boolean isPalindrome(int num) {
        if (num < 0) return false;
        int temp = num, c = 0;
        while (temp != 0) {
            c = c * 10 + temp % 10;
            temp /= 10;
        }
        return num == c;
    }
}
