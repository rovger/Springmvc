package com.rovger.demo.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by weijlu on 2017/3/24.
 */
public class HashMapDemo {

	public static void main(String args[]) {

		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("1", "这是1");
		hashMap.put("2", "这是2");
		hashMap.put("3", "这是3");

		/*for (Map.Entry entry : hashMap.entrySet()) {
			System.out.println(entry.getKey()+"---"+entry.getValue());
		}*/

		for (String key : hashMap.keySet()) {
			System.out.println(key+"---"+hashMap.get(key));
		}


	}
}
