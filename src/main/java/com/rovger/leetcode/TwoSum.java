package com.rovger.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 数组内2个元素之和 等于 target，输出2数下标
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年04月22日 19:29
 */
public class TwoSum {

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        System.out.println(Arrays.toString(twoSum2(nums, target)));
    }

    private static int[] twoSum(int[] nums, int target) {

        Map<Integer, Integer> map = new HashMap(nums.length - 1);
        map.put(nums[0], 0);
        for (int i = 1; i <= nums.length - 1; i++) {
            int temp = target - nums[i];
            if (map.containsKey(temp)) {
                return new int[]{map.get(temp), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    public static int[] twoSum2(int[] nums, int target) {
        int[] index = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = nums.length - 1; j > i; j--) {
                if (nums[i] + nums[j] == target) {
                    index[0] = i;
                    index[1] = j;
                    return index;
                }
            }
        }
        return null;
    }
}