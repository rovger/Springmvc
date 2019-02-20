package com.rovger.demo;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;

/**
 * @Description: TODO
 * @Author weijlu
 * @Date 2018/10/11 14:58
 */
public class Main {

    private static List<Integer> sorted = new LinkedList<>();

    private static void addElements(int e) {

        try {
            File file = new File("C:/temp");
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(isr);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }


    }

    /**
     * byte short int long float double boolean char
     * 最大值+1 == 最小值
     * 最小值-1 == 最大值
     * @param args
     */
    public static void main(String[] args) {
        byte count = 0;
        /*while (true) {
            count++;
            System.out.println("current count value:"+ count);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        addElements(4);
        addElements(3);
        addElements(2);
        addElements(1);
        addElements(4);
        addElements(8);
        addElements(5);

        System.out.println("result:"+ sorted.toString());
        Collections.sort(sorted, new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        File file = new File("C:/demo/score.txt");
        try {
            InputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String lineTxt;
            while ((lineTxt = br.readLine()) != null) {
                System.out.println(lineTxt);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        System.out.println(spaceCounter +" "+ newlineCounter);
    }

}
