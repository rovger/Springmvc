package com.rovger.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by weijlu on 2017/3/21.
 */
public class StringDemo {

	private static char SMALL_LEFT = '(';
	private static char SMALL_RIGHT = ')';
	private static char MIDDLE_LEFT = '[';
	private static char MIDDLE_RIGHT = ']';
	private static char BIG_LEFT = '{';
	private static char BIG_RIGHT = '}';

	public static void main(String[] args) {
		String str = "3}+{4*5-(34+7)/2+5*[3-1]}";
		System.out.println("verified result: "+ verifyStr(str));
	}

	/**
	 * 校验括弧成对出现
	 * 思路：
	 * 	采用“先进后出”表：堆栈Stack
	 * @param str
	 * @return
	 */
	private static boolean verifyStr(String str) {
		Stack<Character> stack = new Stack<>();
		if (str==null || str.isEmpty()) return false;
		for (int i=0; i<str.length(); i++) {
			char current = str.charAt(i);
			if (current==SMALL_LEFT || current==MIDDLE_LEFT || current==BIG_LEFT) {
				stack.push(current);
			}
			if (current==SMALL_RIGHT || current==MIDDLE_RIGHT || current==BIG_RIGHT) {
				if (stack.isEmpty()) return false;
				if (current == SMALL_RIGHT) {
					if (stack.peek() == SMALL_LEFT) {
						stack.pop();
					} else {
						return false;
					}
				} else if (current == MIDDLE_RIGHT) {
					if (stack.peek() == MIDDLE_LEFT) {
						stack.pop();
					} else {
						return false;
					}
				} else if (current == BIG_RIGHT) {
					if (stack.peek() == BIG_LEFT) {
						stack.pop();
					} else {
						return false;
					}
				}
			}
		}
		if (stack.isEmpty()) return true;
		return false;
	}
}
