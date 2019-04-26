package com.rovger.demo.algorithm;

import java.util.*;

/**
 * Created by weijlu on 2017/3/24.
 */
public class ListFormat {

	public static void main(String args[]) {
		List<String> list = new ArrayList<>();
		list.add("a");list.add("b");list.add("c");
		list.add("d");list.add("a");list.add("f");
		list.add("g");list.add("h");list.add("i");
		System.out.println("====before===="+ list.toString());

		List<Integer> indexs = new ArrayList<>();
		indexs.add(6);indexs.add(4);indexs.add(12);
//		List<String> listV2 = exclude3(list, indexs);

		//去除list在eles中已有的元素
		List<String> eles = new ArrayList<>();
		eles.add("a");eles.add("c");eles.add("i");
//		exclude2(list, eles);

		System.out.println("====after===="+ list.toString());

		System.out.println("====remove common===="+ list.toString());
		List<String> list2 = new ArrayList<>();
		list2.add("a");list2.add("m");list2.add("n");
		list2.add("c");list2.add("d");list2.add("r");
		System.out.println("====list===="+ list.toString());
		System.out.println("====list2===="+ list2.toString());
		removeCommon(list, list2);
		System.out.println("====list===="+ list.toString());
		System.out.println("====list2===="+ list2.toString());

		/*int counter = 4;
		List<String> test = new LinkedList<>();
		test.add("1");
		test.add("2");
		test.add("3");
		test.add("4");
		test.add("5");
		test.add("6");
		test.add("7");
		test.add("8");
		test.add("9");
		test.add("10");
		test.add("11");
		test.add("12");
		System.out.println("Remove before:"+ test.toString());

		Iterator it = test.iterator();
		while (true) {
			if (counter <= 0) break;
			System.out.println(it.next());
			it.remove();
			counter--;
		}
		System.out.println("Remove after:"+ test.toString());*/
	}

	/**
	 * 去除list和list2中共同的元素
	 * @param list
	 * @param list2
	 */
	private static void removeCommon(List<String> list, List<String> list2) {
		Iterator<String> it = list.iterator();
		/*if (list.size() <= list2.size()) {
			it = list.iterator();
		} else {
			it = list2.iterator();
		}*/
		while (it.hasNext()) {
			String temp = it.next();
			if (list2.contains(temp)) {
				it.remove();
				list2.remove(temp);
			}
		}
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
	 * list删除通用方法
	 * @param list
	 * @param eles
	 * @param <T>
	 * @return
	 */
	public static <T> void exclude2(List<T> list, List<String> eles) {
		Iterator<T> it = list.iterator();
		while (it.hasNext()) {
			if (eles.contains(it.next())) {
				it.remove();
			}
		}
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
