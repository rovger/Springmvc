package com.rovger.leetcode.algorithm;

import com.rovger.entity.Student;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2021年04月25日 14:47
 */
public class SigDemo {

    /*public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        String testStr = "app_key=b617866c20482d133d5de66fceb37da3&calc_dimension=1&category_id=0&client_os_type=4&count=20&nonce=d15d792875807b0fec620f4db2ac1667&page=1&q=聪明与智慧&timestamp=1533203363618\n";
        String cryRst = Base64.getEncoder().encodeToString(testStr.getBytes());
        System.out.println("src：" + testStr + " -> cryRst：" + cryRst);

        byte[] dryByte = Base64.getDecoder().decode(cryRst.getBytes());
        System.out.println("dry: " + new String(dryByte));

        SecretKeySpec sigKey = new SecretKeySpec("4d8e605fa7ed546c4bcb33dee1381179z0hh5l9A".getBytes(), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(sigKey);
        byte[] hmacSha1 = mac.doFinal(dryByte);
        System.out.println("hmacSha1:"+ hmacSha1);

        byte[] md5 = MessageDigest.getInstance("MD5").digest(hmacSha1);
        System.out.println(Hex.encodeHexString(md5));
    }*/

    public static void main(String[] args) {
        Map<String, List<Student>> stuMap = new HashMap<>();
        List<Student> list1 = new ArrayList<>();
        list1.add(new Student("weijie", 12));
        list1.add(new Student("lily", 13));

        List<Student> list2 = new ArrayList<>();
        stuMap.put("backend", list1);
        stuMap.put("frontend", list2);

        System.out.println(stuMap);

        int num = 15;
        List<Student> rstList = new ArrayList<>(stuMap.size());
        stuMap.forEach((k, v) -> {
            if (!CollectionUtils.isEmpty(v)) {
                rstList.add(v.get(num % v.size()));
            }
        });

        System.out.println(rstList);
    }

}
