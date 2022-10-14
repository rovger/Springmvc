package com.rovger.utils;

/**
 * @Description: TODO
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2021年03月22日 09:56
 */
public class TryCatchDemo {

    public static void main(String[] args) {

        Integer[] nums = new Integer[]{12,34,5,6,7};
//        String[] nums = new String[]{"12", "34", "45"};
        Integer num = getArrayNum(nums, 2);
        System.out.println(num);
    }

    private static String getArrayNum(String[] nums, int idx) {
        String num = null;
        try {
            num = nums[idx];
            return num;
        } catch (Exception e) {
            e.printStackTrace();
            return "wei";
        } finally {
            return "10";
        }
    }

    private static int getArrayNum(Integer[] nums, int idx) {
        int num = 0;
        try {
            num = nums[idx];
            return num;
        } catch (Exception e) {
            e.printStackTrace();
            return num;
        } finally {
            return 10;
        }
    }

}
