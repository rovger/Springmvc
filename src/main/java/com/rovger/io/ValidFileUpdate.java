package com.rovger.io;

import org.springframework.core.NamedThreadLocal;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description: 使用MD5对文件内容进行加密，得到加密串进行比较，这种方式没法判断文件大小
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年10月13日 17:20
 */
public class ValidFileUpdate {

    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final ThreadLocal<MessageDigest> MESSAGE_DIGEST_THREAD_LOCAL =
            new NamedThreadLocal("MESSAGE_DIGEST_THREAD_LOCAL") {
                @Override
                public MessageDigest initialValue() {
                    try {
                        return MessageDigest.getInstance("MD5");
                    } catch (NoSuchAlgorithmException e) {
                        return null;
                    }
                }
            };

    /**
     * byte[] -> md5Hex
     *
     * @param bytes
     * @return
     */
    public static String md5Hex(byte[] bytes) {
        MessageDigest messageDigest = MESSAGE_DIGEST_THREAD_LOCAL.get();
        if (messageDigest != null) {
            return encodeHexString(messageDigest.digest(bytes));
        }
        throw new RuntimeException("...");
    }

    private static String encodeHexString(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(DIGITS_LOWER[(bytes[j] >> 4) & 0x0f]);
            buf.append(DIGITS_LOWER[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        File file = new File("src/main/java/com/rovger/io/task");
        System.out.println(file.length());
        System.out.println(md5Hex(file2Bytes(file)));
    }

    /**
     * 文件 -> 字节数组
     *
     * @param file
     * @return
     */
    public static byte[] file2Bytes(File file) {
        // 缓冲读流
        FileInputStream in = null;
        // 字节数组写流
        ByteArrayOutputStream out = null;

        byte[] bytes = null;
        try {
            in = new FileInputStream(file);
            out = new ByteArrayOutputStream();

            int n;
            byte[] b = new byte[1024];
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            bytes = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }
}
