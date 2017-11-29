package com.rovger.demo;

import java.math.BigDecimal;

/**
 * Created by weijlu on 2017/6/6.
 */
public class BigDecimalDemo {

    public static void main(String[] args) {

        BigDecimal total = new BigDecimal("11");
        BigDecimal target = new BigDecimal("11");

        String percentage = "0";
        if (total.longValue()!=0L && target.longValue()!=0L) {
            double divide = target.divide(total, 3, BigDecimal.ROUND_HALF_UP).doubleValue();
            percentage = String.valueOf(divide*100) + "%";
        }

        System.out.println(percentage);

    }
}
