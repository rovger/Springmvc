package com.rovger.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年04月24日 16:41
 */
public class LongestSubString {

    public static void main(String[] args) {
        String s = "abcadebcbbd";
        System.out.println(lengthOfLongestSubString(s));
    }

    /**
     * 滑动窗口: abcabcbb
     *
     * @param s
     * @return
     */
    private static int lengthOfLongestSubString2(String s) {
        Map<Character, Integer> map = new HashMap<>(s.length());
        int left = 0, max = 0;
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                // 如果有map key重复，滑块右移
                left = Math.max(left, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            max = Math.max(max, i - left + 1);
        }
        return max;
    }

    /**
     * 双指针解法
     * <p>
     * （a）bcabcbb -> （abc）abcbb
     * a（b）cabcbb -> a（bca）bcbb
     * ab（c）abcbb -> ab（cab）cbb
     * abc（a）bcbb -> abc（abc）bb
     * abca（b）cbb -> abca（bc）bb
     * abcab（c）bb -> abcab（cb）b
     * abcabc（b）b -> abcabc（b）b
     * abcabcb（b） -> abcabcb（b）
     *
     * @param s
     * @return
     */
    private static int lengthOfLongestSubString(String s) {
        // 哈希集合，记录元素是否存在过
        Set<Character> set = new HashSet<>();
        int left = 0, right = 0;
        int max = 0;
        while (right < s.length()) {
            char t = s.charAt(right);
            if (set.contains(t)) {
                set.remove(s.charAt(left));
                left++;
            } else {
                set.add(t);
                max = Math.max(max, right - left + 1);
                // 没有重复，right边界右移
                right++;
            }
        }
        return max;
    }

}
