package com.rovger.utils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.rovger.entity.Student;

/**
 * @Description: TODO
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2023年01月12日 17:13
 */
public class Secrets {

    private static final Table<String, String, Student> storage = HashBasedTable.create();

    static {
        storage.put("A", "android", new Student("weijie", 30));
        storage.put("B", "iphone", new Student("hahaha", 31));
    }

    public static Student getSecretByBundleId(String rowKey, String deviceType) {
        final Student secret = storage.get(rowKey, deviceType);
        return secret;
    }

    public static void main(String[] args) {
        System.out.println(Secrets.getSecretByBundleId("B", "iphone"));
    }
}
