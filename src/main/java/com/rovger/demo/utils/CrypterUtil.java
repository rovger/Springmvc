package com.rovger.demo.utils;

import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class CrypterUtil {

    public static final String SIGN_ALGORITHMS = "SHA1PRNG";
    private static final String UTF_8 = "UTF-8";
    private static final String AES = "AES";
    private static final int AES_KEY_SIZE = 128;
    private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
    private static final String HMAC_SHA1 = "HmacSHA1";

    /*
     * -------------------------------------------------------------------------------
     * HMAC SHA1摘要算法，该算法特点是：
     * <li>对于相似度较高的字符串得到的签名值差异比较大</li>
     * <li>常用作签名算法</li>
     * -------------------------------------------------------------------------------
     */

    /**
     * 利用AES算法生成随机key
     *
     * @return
     * @throws Exception
     */
    public static String getRandKey() {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(AES);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("AES algorithm not supported");
        }
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(AES_KEY_SIZE, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        return DigestUtils.md5Hex(secretKey.getEncoded());
    }

    /**
     * 利用AES算法 对称加密
     *
     * @param content
     * @param encryKey
     * @return
     */
    public static byte[] encryptStr(String content, String encryKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(AES);
            SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
            random.setSeed(encryKey.getBytes(UTF_8));
            kgen.init(AES_KEY_SIZE, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] encodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(encodeFormat, AES);
            Cipher cipher = Cipher.getInstance(AES);
            byte[] byteContent = content.getBytes(UTF_8);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(byteContent);
        } catch (Exception e) {
            throw new RuntimeException("AES encrypt filed.", e);
        }
    }

    /**
     * 利用AES算法 对称解密
     *
     * @param encryptStr: 密文
     * @param decryKey
     * @return
     */
    public static String decryptStr(String encryptStr, String decryKey) {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] bytes = decoder.decode(encryptStr);

            KeyGenerator kGen = KeyGenerator.getInstance(AES);
            SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
            random.setSeed(decryKey.getBytes(StandardCharsets.UTF_8));
            kGen.init(AES_KEY_SIZE, random);
            SecretKey secretKey = kGen.generateKey();
            byte[] encodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(encodeFormat, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(bytes));
        } catch (Exception e) {
            throw new RuntimeException("AES decrypt filed.", e);
        }
    }

    public static void main(String[] args) {
        String content = "android8#23423-32#weijie#test#byebye";
        String key = "7721f10cb16aeb985303615f8e9f4aa5";
        String encryptStr = new BASE64Encoder().encode(encryptStr(content, key));
        System.out.println(encryptStr);

        System.out.println(CrypterUtil.decryptStr(encryptStr, key));

    }

    public static byte[] hmacSHA1(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        if (data == null || key == null) {
            return null;
        }

        SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(signingKey);
        return mac.doFinal(data);
    }

    /**
     * HMAC SHA1签名或摘要算法
     *
     * @param data 待摘要的字节数据
     * @param key  使用的key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String hmacSHA1ToStr(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        return DigestUtils.md5Hex(hmacSHA1(data, key));
    }

    /**
     * HMAC SHA1签名或摘要算法
     *
     * @param data
     * @param key
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static String hmacSHA1ToStr(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException {
        if (data == null || key == null) {
            return null;
        }
        return hmacSHA1ToStr(data.getBytes(), key.getBytes());
    }

    public static String getMd5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes("utf-8"));
            // digest()最后确定返回md5 hash值，返回值为8为字符串。
            return toHex(md.digest());
        } catch (Exception e) {
            return null;
        }
    }

    private static String toHex(byte[] data) {
        char[] chars = new char[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            chars[i * 2] = HEX_DIGITS[(data[i] >> 4) & 0xf];
            chars[i * 2 + 1] = HEX_DIGITS[data[i] & 0xf];
        }
        return new String(chars);
    }

    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHex2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
