package com.rovger.demo;

import java.util.Scanner;

/**
 * @Description: TODO
 * @Author weijlu
 * @Date 2018/10/11 14:58
 */
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a character: ");
        String input = "q e r r \r\n \r\n a";//sc.nextLine();
        System.out.println(input);
        /*char asc = input.charAt(0);
        System.out.println((char)(asc-1) +" "+ asc +" "+ (char)(asc+1));
        System.out.println(asc-1 +" "+ asc +" "+ (asc+1));*/
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
        System.out.println(spaceCounter +" "+ newlineCounter);
    }

}
