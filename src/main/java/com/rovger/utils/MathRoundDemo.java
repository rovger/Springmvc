package com.rovger.utils;

import java.util.Random;

/**
 * Created by weijlu on 2017/3/30.
 */
public class MathRoundDemo {

    public static void main(String[] args) {
		/*int num1 = 2;
		int num2 = 3;
		System.out.print(Math.round(num1 / num2 * 10000) / 100.00 + "%");*/

        System.out.println(new Random().nextInt(100));

        int disturbanceSeconds = 120;
        int distSeconds = (int) (Math.random() * disturbanceSeconds);
        System.out.println(distSeconds);
        // 只有末尾一个数为1时，才会返回true，如：10进制49的二进制110001 & 000001（二进制计算方式1*2的0次方）
        System.out.println((distSeconds & 1) == 0 ? distSeconds : -distSeconds);

        //System.out.println("---"+TimeType.DAILY.getNickname());
    }
}
