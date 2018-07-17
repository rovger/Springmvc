package com.rovger.demo;

import java.util.*;

/**
 * Created by weijlu on 2017/3/24.
 */
public class ListRemoveDemo {

	public static void main(String args[]) {
		List<String> list = new ArrayList<>();
		list.add("a");list.add("b");list.add("c");
		list.add("d");list.add("a");list.add("f");
		list.add("g");list.add("h");list.add("i");
		System.out.println("====before===="+ list.toString());
		List<Integer> indexs = new ArrayList<>();
		indexs.add(6);indexs.add(4);indexs.add(12);
		List<String> listV2 = exclude3(list, indexs);
		System.out.println("====after===="+ listV2.toString());
	}

	/**
	 * 这种方法要确保list中没有重复数据
	 * 使用倒序保证删除一个元素，下标变化不会影响前面
	 * ====before====[a, b, c, d, e, f, g, h, i]
	 * ====after====[a, b, c, d, f, h, i]
	 * @param list
	 * @param indexs
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> exclude1(List<T> list, List<Integer> indexs) {
		if (indexs == null || indexs.size() == 0) return list;
		if (list == null || list.size() == 0) return list;
		//对indexs排序,升序
		Collections.sort(indexs, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1-o2;
			}
		});
		//遍历元素，按下标倒序删除可靠，如果按元素删除会导致同时删除list中重复的
		for (int i=indexs.size()-1; i>=0; i--) {
			int index = indexs.get(i);
			if (index < list.size()) {
				list.remove(list.get(index));
			}
		}
		return list;
	}

	/**
	 * 可以保证即使list中有重复数据也可以删除
	 * @param list
	 * @param indexs
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> exclude2(List<T> list, List<Integer> indexs) {
		List<T> removedList = new ArrayList<>();
		for (int i=0; i<list.size(); i++) {
			if (indexs.contains(i)) {
				continue;
			} else {
				removedList.add(list.get(i));
			}
		}
		return removedList;
	}

	/**
	 * 最优解决方案，可以确保重复元素不被误删
	 * @param list
	 * @param indexs
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> exclude3(List<T> list, List<Integer> indexs) {
		//对indexs进行升序排序
		/*Collections.sort(indexs, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o1-o2;
			}
		});*/
		for (int i=list.size()-1; i>=0; i--) {
			if (indexs.contains(i)) {
				list.remove(i);
			}
		}
		return list;
	}
}
