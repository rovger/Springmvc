package com.rovger.demo.algorithm;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @Description: prime：质数(素数)
 * 质数：大于1的自然数中，除了1和它本身以外不再有其他因数的自然数
 * 因数：指整数a除以整数b(b != 0)的商正好是整数而没有余数，我们称b是a的因数
 * <p>
 * 34不是质数，因为2*17=34
 * 47是质数
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年08月18日 09:47
 */
public class PrimeEncrypt {

    public static void main(String[] args) {
//        String guid = getGUID();
        String guid = getGUIDV2();
        String guid3 = nonce();
        System.out.println("Guid: " + guid);
        System.out.println("Guid3: " + guid3);

        Random rd = new Random(1000);
        // 相同种子数的Random对象，生成的随机数字相同，不安全，且生成的值可预测
        System.out.println(rd.nextInt());

        /**
         * SecureRandom和Random都是，也是如果种子一样，产生的随机数也一样：因为种子确定，随机数算法也确定，因此输出是确定的。
         * 只是说，SecureRandom类收集了一些随机事件，比如鼠标点击，键盘点击等等，SecureRandom 使用这些随机事件作为种子。
         * 这意味着，种子是不可预测的，而不像Random默认使用系统当前时间的毫秒数作为种子，有规律可寻
         */
        SecureRandom sr = new SecureRandom();
        System.out.println(sr.nextInt(37));

    }

    /**
     * 随机字符串，但不等于全局唯一ID
     */
    private static final String AES = "AES";
    private static final int AES_KEY_SIZE = 128;
    private static String nonce() {
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
     * 随机字符串，但不等于全局唯一ID
     */
    private static String getGUIDV2() {
        return RandomStringUtils.randomAlphanumeric(32);
    }

    /**
     * 生成16位不重复的随机数，含数字+大小写（不咋样）
     *
     * @return
     */
    private static String getGUID() {
        StringBuilder sb = new StringBuilder();
        //产生16位的强随机数
        Random rd = new SecureRandom();
        for (int i = 0; i < 16; i++) {
            //产生0-2的3位随机数
            int type = rd.nextInt(3);
            switch (type) {
                case 0:
                    //0-9的随机数
                    sb.append(rd.nextInt(10));
                    break;
                case 1:
                    //ASCII在65-90(10进制)之间为大写,获取大写随机
                    sb.append((char) (rd.nextInt(25) + 65));
                    break;
                case 2:
                    //ASCII在97-122之间为小写，获取小写随机
                    sb.append((char) (rd.nextInt(25) + 97));
                    break;
                default:
                    break;
            }
        }
        return sb.toString();
    }
}
