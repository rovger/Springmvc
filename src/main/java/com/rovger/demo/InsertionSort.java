package com.rovger.demo;

/**
 * Created by weijlu on 2017/8/8.
 */
public class InsertionSort {

    public static void main(String[] args) {
        /**
         * 插入法排序
         */
        int[] arr = new int[]{34, 8, 64, 51, 32, 21};
        for (int i=1; i<arr.length; i++) {
            int temp = arr[i];
            int j = i;
            while (j>0 && temp<arr[j-1]) {
                arr[j] = arr[j-1];
                j--;
            }
            arr[j] = temp;
        }

        /**
         * 递归二分法查找
         */
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(searchRecursive(array, 9));

        /**
         * 查找某位数字在二维数组中的位置
         */
        int[][] doubleArray = {{1,2,3}, {4,5,6}, {7,8,9}, {10,11,12}, {13,14,15}};
        System.out.println(searchNumInDoubleArray(doubleArray, 5, 3, 8));

        /**
         * 二分法
         */
        System.out.println(dichotomyArrayQuery(doubleArray, 13));

        /**
         * 分治法，时间复杂度0(n)，优于二分法算法
         */
        System.out.println(divideArrayQuery(doubleArray, 11));
    }

    /**
     * 分治法
     * @param doubleArray
     * @param findValue
     * @return
     */
    private static String divideArrayQuery(int[][] doubleArray, int findValue) {
        if (doubleArray==null || doubleArray.length==0) return "not found!";
        int rows = doubleArray.length, cols = doubleArray[0].length;
        int currentRow = 0, currentCol = cols-1;
        while (currentRow<rows && currentCol>=0) {
            if (doubleArray[currentRow][currentCol]==findValue) {
                return "location: ("+ currentRow +","+ currentCol +")";
            } else if (doubleArray[currentRow][currentCol]>findValue) {
                currentCol--;
            } else {
                currentRow++;
            }
        }
        return "not found!";
    }

    /**
     * 二分法
     * @return
     */
    private static String dichotomyArrayQuery(int[][] doubleValue, int findValue) {
        if (doubleValue.length==0) return "array can not be null";
        int rows = doubleValue.length, cols = doubleValue[0].length;
        int middle, rowup = 0, rowdown = rows-1;
        //查找findValue可能所在行下线
        while (rowup < rowdown) {
            middle = (rowup+rowdown)/2;
            if (doubleValue[middle][0] < findValue) {
                rowup = middle + 1;
            } else if (doubleValue[middle][0] > findValue) {
                rowdown = middle - 1;
            } else {
                return "location: ("+ middle +","+ 0 +")";
            }
        }
        //对可能所在行进行二分查找
        for (int i=0; i<=rowdown; i++) {
            int start = 0, end = cols-1;
            while (start <= end) {
                middle = (start+end)/2;
                if (doubleValue[i][middle] < findValue) {
                    start = middle+1;
                } else if (doubleValue[i][middle] > findValue) {
                    end = middle-1;
                } else {
                    return "location: ("+ i +","+ middle +")";
                }
            }
        }
        return "not found!";
    }


    /**
     * 查找给定数字在二维数组中位置(非二分法，右上角思路)
     * @param doubleArray
     * @param findValue
     * @return
     */
    private static String searchNumInDoubleArray(int[][] doubleArray, int rows, int cols, int findValue) {
        if (doubleArray==null || rows==0 || cols==0) return "parameter error";
        for (int row=0, col=cols-1; row<rows && col>=0; ) {
            if (doubleArray[row][col]==findValue) {
                return "location: ("+ row +","+ col +")";
            } else if (doubleArray[row][col]>findValue) {
                col--;
            } else {
                row++;
            }
        }
        return "not found!";
    }


    /**
     * 二分法查找一维数组中数字位置
     * @param array
     * @param findValue
     * @return
     */
    private static int searchRecursive(int[] array, int findValue) {
        if (array==null || array.length==0) return -1;
        int middle, start = 0, end = array.length-1;
        //二分法
        while (start <= end) {
            middle = (start+end)/2;
            if (array[middle]<findValue) {
                start = middle+1;
            } else if (array[middle]>findValue) {
                end = middle-1;
            } else {
                return middle;
            }
        }
        return -1;
        //递归二分法
        /*if (middleValue==findValue) {
            return middle;
        } else if (middleValue>findValue) {
            return searchRecursive(array, start, middle-1, findValue);
        } else {
            return searchRecursive(array, middle+1, end, findValue);
        }*/
    }
}
