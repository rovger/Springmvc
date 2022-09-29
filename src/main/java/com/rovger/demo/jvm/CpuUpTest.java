package com.rovger.demo.jvm;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Description: TODO
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2021年06月29日 09:29
 */
public class CpuUpTest {

    public static void main(String[] args) {

//        Object o = new Object();
//        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        int tmp = 1;
        while (tmp < 10) {
            tmp = tmp * 10 / 10 + 10 -10;
            System.out.println(tmp);
        }

    }
}
