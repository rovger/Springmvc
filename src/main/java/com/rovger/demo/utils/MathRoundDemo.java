package com.rovger.demo.utils;

/**
 * Created by weijlu on 2017/3/30.
 */
public class MathRoundDemo {

	public static void main(String[] args) {
		int num1 = 2;
		int num2 = 3;
		System.out.print(Math.round(num1 / num2 * 10000) / 100.00 + "%");
	}
}
