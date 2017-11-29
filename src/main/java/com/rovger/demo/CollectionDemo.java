package com.rovger.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weijlu on 2017/4/10.
 */
public class CollectionDemo {

	private static Map<String, String> walletSetupMap = new HashMap<String, String>();

	static {
		walletSetupMap.put("600500", "Load_Success");
		walletSetupMap.put("600700", "Load_Success");
		walletSetupMap.put("600900", "Load_Success");
		walletSetupMap.put("600600", "Create_Success");
		walletSetupMap.put("600800", "Create_Success");
		walletSetupMap.put("600909", "Create_Success");

	}

	public static void main(String[] args) {
		List<String> searchKey = getkeys(walletSetupMap, "Load_Success");
		System.out.println(searchKey.toArray());
	}

	private static List<String> getkeys(Map<String, String> amap, String value) {
		List<String> retKeys = new ArrayList<String>();
		for(String key : amap.keySet()) {
			if(amap.get(key).equals(value)) {
				retKeys.add(key);
			}
		}
		return retKeys;
	}
}
