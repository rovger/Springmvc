package com.rovger.task.work;

import com.rovger.Initializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by weijlu on 2017/4/1.
 */
public class Task3 implements Runnable {

	Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void run() {

		String now = sdf.format(new Date());

		logger.info("当前任务： " + this.getClass().getSimpleName() + "， 当前用户：" + Initializer.userName
				+ "， 是否初始化成功：" + Initializer.isInitialized + "， 当前时间：" + now);

	}
}
