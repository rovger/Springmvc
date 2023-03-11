package com.rovger.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @Description: AES加解密算法
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2021年01月06日 17:57
 */
public class CrypterUtilV2 {

    private static final Logger log = LoggerFactory.getLogger(CrypterUtil.class);

    private static final String AES = "AES";
    private static final String CIPHER_MODE = "AES/ECB/PKCS5Padding";
    private static final String UTF_8 = "UTF-8";

    public static void main(String[] args) throws Exception {
        String toEncryptStr = "android#luweijie#test";
        String key = "9f9ef8f10bebeaa83e71e62f935bede8";

        String encrypt = encrypt(toEncryptStr, key);
        System.out.println("AESCipher.main encrypt = " + encrypt);
        String decrypt = decrypt(encrypt, key);
        System.out.println("AESCipher.main decrypt = " + decrypt);
    }

    /**
     * 对字符串加密
     *
     * @param key  密钥
     * @param decryptStr 源字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String decryptStr, String key) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), AES);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(decryptStr.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对字符串解密
     *
     * @param key  密钥
     * @param encryptStr 已被加密的字符串
     * @return 解密得到的字符串
     */
    public static String decrypt(String encryptStr, String key) throws Exception {
        try {
            String srcDecode = URLDecoder.decode(encryptStr, UTF_8);
            byte[] encrypted = Base64.decodeBase64(srcDecode);
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), AES);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] original = cipher.doFinal(encrypted);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
