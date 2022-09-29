package com.rovger.demo;

import org.openjdk.jol.info.ClassLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by weijlu on 2017/6/6.
 */
public class BigDecimalDemo {

    public static void main(String[] args) {

        /*BigDecimal bigDecimal = new BigDecimal("");
		System.out.print(bigDecimal.longValue());*/

		/*int total = 3;
		System.out.print(1%3);*/

        BigDecimal bd1 = new BigDecimal(0.01);
        BigDecimal bd2 = BigDecimal.valueOf(0.01);
        BigDecimal bd3 = BigDecimal.valueOf(0.02);
        System.out.println("bd1:" + bd1);
        System.out.println("bd2:" + bd2);
        System.out.println(bd1.equals(bd2));
        System.out.println(bd1.compareTo(bd2));
        System.out.println(bd3.compareTo(bd2));

        BigDecimal total = new BigDecimal("11");
        BigDecimal target = new BigDecimal("11");
        String percentage = "0";
        if (total.longValue() != 0L && target.longValue() != 0L) {
            double divide = target.divide(total, 3, RoundingMode.HALF_UP).doubleValue();
            percentage = String.valueOf(divide * 100) + "%";
        }

        System.out.println(percentage);

    }
}
