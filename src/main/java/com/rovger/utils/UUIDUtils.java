package com.rovger.utils;

import java.util.UUID;

/**
 * @Description: TODO
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2023年02月03日 14:50
 */
public class UUIDUtils {

    public static void main(String[] args) {
        System.out.println(UUID2UpperCase());
    }

    public static String UUID2UpperCase() {
        return UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }

}
