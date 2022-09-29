package com.rovger.demo.algorithm;

/**
 * @Description: 二分法
 * @Author weijlu
 * @Date 2019/4/12 13:31
 */
public class Erfenfa {

    public static void main(String[] args) {
        int[][] doubleArray = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}, {13, 14, 15}};
        int[] sorted = {1, 2, 3, 3, 4, 5, 5, 6, 6};
        int firstLargeKey = firstLargeKey(sorted, 3);
        int indexOfKey = indexOfKey(sorted, 4);
        int firstEqualIndex = firstEqualIndex(sorted, 8);
        int lastEqualIndex = lastEqualIndex(sorted, 3);
        int firstEqualAndBig = firstEqualAndBig(sorted, 5);
        int lastSmallerThatKey = lastSmallerThatKey(sorted, 4);
        String indexOfDoubleArray = indexOfDoubleArray(doubleArray, 17);
        System.out.println(indexOfDoubleArray);
    }

    /**
     * 二分法查找指定元素在二维数组中的坐标
     *
     * @param array
     * @param key
     * @return
     */
    private static String indexOfDoubleArray(int[][] array, int key) {
        int rows = array.length, cols = array[0].length;
        int rowup = 0, rowdown = rows - 1;
        while (rowup < rowdown) {
            int mid = (rowup + rowdown) / 2;
            if (array[mid][0] > key) {
                rowdown = mid - 1;
            } else if (array[mid][0] < key) {
                rowup = mid + 1;
            } else {
                return "(" + mid + ", 0)";
            }
        }
        //对可能存在的行进行遍历
        int left = 0, right = cols - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[rowdown][mid] < key) {
                left = mid + 1;
            } else if (array[rowdown][mid] > key) {
                right = mid - 1;
            } else {
                return "(" + rowdown + ", " + mid + ")";
            }
        }

        return "Not Found";
    }

    private static int lastSmallerThatKey(int[] array, int key) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] >= key) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }

    private static int firstEqualAndBig(int[] array, int key) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] >= key) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private static int lastEqualIndex(int[] array, int key) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] <= key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        if (right >= 0 && array[right] == key) {
            return right;
        }
        return -1;
    }

    private static int firstEqualIndex(int[] array, int key) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] >= key) {
                right = mid - 1;
            }
            if (array[mid] < key) {
                left = mid + 1;
            }
        }
        if (left < array.length && array[left] == key) {
            return left;
        }
        return -1;
    }

    private static int indexOfKey(int[] array, int key) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] == key) {
                return mid;
            } else if (array[mid] < key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    private static int firstLargeKey(int[] array, int key) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] > key) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return array[left];
    }
}
