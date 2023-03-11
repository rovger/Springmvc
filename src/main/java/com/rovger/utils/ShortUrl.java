package com.rovger.utils;

/**
 * @author weijlu
 * @version 1.0
 * @description 短信短链
 * @date 2023/3/4 17:28:15
 */
import java.util.Random;

public class ShortUrl {

    // 定义62进制的字符表
    private static final String BASE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // 定义短链的长度
    private static final int LENGTH = 7;

    // 定义随机数生成器
    private static final Random RANDOM = new Random();

    // 将10进制数字转换为62进制字符串
    public static String toBase62(long num) {
        StringBuilder result = new StringBuilder();
        while (num > 0) {
            int remainder = (int) (num % 62);
            result.append(BASE.charAt(remainder));
            num /= 62;
        }
        return result.reverse().toString();
    }

    // 将长链接转换为短链接
    public static String shorten(String longUrl) {
        // 使用MD5对长链接进行加密，得到32位的16进制字符串
        String md5Hex = org.apache.commons.codec.digest.DigestUtils.md5Hex(longUrl);

        // 将加密后的字符串分成4段，每段8位
        String[] segments = new String[4];
        for (int i = 0; i < 4; i++) {
            segments[i] = md5Hex.substring(i * 8, (i + 1) * 8);
        }

        // 对每段字符串进行处理，得到4个候选短链接
        String[] candidates = new String[4];
        for (int i = 0; i < 4; i++) {
            // 将每段字符串转换为长整型数字
            long num = Long.parseLong(segments[i], 16);

            // 将数字转换为62进制字符串，并补齐长度为6位
            String base62Str = toBase62(num);
            while (base62Str.length() < LENGTH) {
                base62Str += "0";
            }

            // 将候选短链接保存起来
            candidates[i] = base62Str;
        }

        // 随机选择一个候选短链接作为最终结果，并添加前缀http://t.cn/
        int index = RANDOM.nextInt(4);
        return "http://t.cn/" + candidates[index];
    }

    public static void main(String[] args) {
        System.out.println(shorten("http://www.weijie.com/test?name=weijie&age=30&gender=1"));
    }

}
