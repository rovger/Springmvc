package com.rovger.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by weijlu on 2017/3/20.
 */
public class DateFormat {
	public static void main(String args[]) {
		/*String time = "2017-03-20T00:46:23Z";
		String date_time = "2017-03-20T01:00:37.000Z";
		try {
			parse(date_time, "yyyy-MM-dd'T'HH:mm:ss'Z'");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}*/
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("当前时间：" + sdf.format(now));


	}

	private static void parse(String time, String stringPatten) throws ParseException {
		if (time.indexOf(".") >= 0) {
			stringPatten = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(stringPatten);
		sdf.parse(time);
	}
}
