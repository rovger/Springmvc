package com.rovger.demo.algorithm;

/**
 * @Description: 找出数组中出现奇/偶数次的一个元素
 * @Author weijlu
 * @Date 2019/4/16 10:46
 */
public class ArrayFind {

    public static void main(String[] args) {
        /*int[] array = {1, 1, 2, 3, 3, 5, 5, 6, 6};
        int num = find_jishuci_ele(array);
        System.out.println(num);*/

        int[] array1 = {2, 1, 5, 3, 9};
        int[] array2 = {4, 5, 6, 7, 8};
        find_sort_common(array1, array2);
//        find_unsort_common(array1, array2);

    }

    /**
     * 有序2个数组，找出相同的元素
     * 1. 实现方案是，分别循环这2个数组
     * 2. 也可以一个数组作为目标，用二分法查找
     *
     * @param a
     * @return
     */
    private static void find_sort_common(int[] a, int[] b) {
        int i = 0, j = 0;
        while (i < a.length && j < b.length) {
            if (a[i] > b[j]) {
                j++;
            } else if (a[i] < b[j]) {
                i++;
            } else {
                System.out.println("共同的元素：" + a[i]);
                i++;
                j++;
            }
        }
    }

    /**
     * 2个无序的数组，查找共同元素，
     * 先对一个数组进行排序（插入排序）
     * 再利用，未排序的数组对排序后的数组进行二分法查找
     *
     * @param a
     * @param b
     */
    private static void find_unsort_common(int[] a, int[] b) {
        //对a数组进行插入排序：{2, 1, 5, 3, 9};
        /*for (int i=1; i<a.length; i++) {
            int temp = a[i];
            int j = i;
            for ( ; j>0 && temp<a[j-1]; j--) {
                a[j] = a[j-1];
            }
            a[j] = temp;
        }*/
        //对a数组进行冒泡排序
        int temp;
        for (int i = 0; i < a.length - 1; i++) {//冒泡次数
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    temp = a[j + 1];
                    a[j + 1] = a[j];
                    a[j] = temp;
                }
            }
        }
        System.out.println("a数组排序后：");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + "\t");
        }
        for (int i = 0; i < b.length; i++) {
            int key = b[i];
            int start = 0, end = b.length - 1;
            while (start <= end) {
                int mid = (start + end) / 2;
                if (a[mid] < key) {
                    start = mid + 1;
                } else if (a[mid] > key) {
                    end = mid - 1;
                } else {
                    System.out.println("重复元素：" + a[mid]);
                    break;
                }
            }
        }
    }

    /**
     * 思路：转化为二进制，使用异或处理，应为偶数出现次数的1或者0，异或的结果都是0，且0异或任何数都是任何数，那么对return的结果没有任何影响
     * <p>
     * 或者，借用Map数据结构，for循环数组，只要出现第二次的element，就将其remove掉
     *
     * // 异或测试
     * System.out.println("异或/半加运算(不带进位的二进制加法)：" + (4 ^ 4));
     *
     * @param array
     * @return
     */
    private static int find_jishuci_ele(int[] array) {
        int res = 0;
        for (int val : array) {
//            res ^= val;
            res = res ^ val;
            System.out.println(res);
        }
        return res;
    }

}
