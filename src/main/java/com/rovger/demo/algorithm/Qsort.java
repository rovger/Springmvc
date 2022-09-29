package com.rovger.demo.algorithm;

/**
 * @Description: 快速排序, 优势：每次循环可以决定一个元素的位置，即基准值的位置
 * @Author weijlu
 * @Date 2019/4/17 9:47
 */
public class Qsort {

    public static void main(String[] args) {
        int[] array = {3, 5, 7, 2, 6, 1, 3, 8, 4};
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.println();
        qSort(array, 0, array.length - 1);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ", ");
        }
    }

    /**
     * 先找一个基准值，默认我们使用: key = array[0]，这里就是：3
     *
     * @param array
     */
    private static void qSort(int[] array, int left, int right) {
        if (left > right) return;
        int low = left;
        int height = right;
        int key = array[low]; // 基准值
        while (low < height) {
            for (; ; height--) {
                if (low == height) break;
                if (array[height] < key) {
                    array[low] = array[height];
                    break;
                }
            }
            for (; ; low++) {
                if (low == height) break;
                if (array[low] > key) {
                    array[height] = array[low];
                    break;
                }
            }
        }
        array[low] = key;
        // 到这步为止，原数组被分成2部分，小于基准数key的都被放在前面，大于的都被放在后面：1, 2, 3, 7, 6, 5, 3, 8, 4,
        // 分而治之，对分割的数组再进行分别：迭代
        qSort(array, left, low - 1);
        qSort(array, low + 1, right);
    }
}
