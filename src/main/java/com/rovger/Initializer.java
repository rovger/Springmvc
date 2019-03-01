package com.rovger;

import com.rovger.controller.HomeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by weijlu on 2017/4/1.
 */
public class Initializer {
	private static final Logger logger = LoggerFactory.getLogger(Initializer.class);
	public static String userName;
	public static boolean isInitialized = false;

	public void init() {
		userName = "Rovger";
		isInitialized = true;
		logger.info("正在执行Initializer...");
		logger.info("执行完毕Initializer...");
	}
}
