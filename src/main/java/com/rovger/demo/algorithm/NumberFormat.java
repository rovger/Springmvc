package com.rovger.demo.algorithm;

import java.util.*;

/**
 * @Description: TODO
 * @Author weijlu
 * @Date 2018/10/11 14:58
 */
public class NumberFormat {

    /**
     * byte short int long float double boolean char
     * 最大值+1 == 最小值
     * 最小值-1 == 最大值
     * @param args
     */
    public static void main(String[] args) {

        float num_1 = 12.1f;
        //将float转化为int
//        int num_2 = (int) num_1;
        int num_2 = Float.valueOf(num_1).intValue();
        System.out.println(num_1 +"==>"+ num_2);

        System.out.println("=================");

        int num_3 = 2;
        float num_4 = 3.6f;
        int num_5 = (int) (num_3*num_4);
        System.out.println(num_5);

        System.out.println("=================");

        String str = "123";
        int num_6 = Integer.parseInt(str);
        String.valueOf(num_6);

        /*
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a character: ");
        String input = "q e r r \r\n \r\n a";//sc.nextLine();
        System.out.println(input);
        char asc = input.charAt(0);
        System.out.println((char)(asc-1) +" "+ asc +" "+ (char)(asc+1));
        System.out.println(asc-1 +" "+ asc +" "+ (asc+1));
        int spaceCounter = 0;
        int newlineCounter = 0;
        for (int i=0; i<input.length(); i++) {
            char element = input.charAt(i);
            if (' ' == element) {
                spaceCounter++;
                continue;
            }
            if ('\r' == element) {
                newlineCounter++;
            }
        }
        System.out.println(spaceCounter +" "+ newlineCounter);*/
    }

}
