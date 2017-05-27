package com.rovger;

/**
 * Created by weijlu on 2017/4/1.
 */
public class Initializer {

	public static String userName;
	public static boolean isInitialized = false;

	public void init() {
		userName = "Rovger";
		isInitialized = true;
		System.out.println("正在执行Initializer...");
		System.out.println("执行完毕Initializer...");
	}
}
