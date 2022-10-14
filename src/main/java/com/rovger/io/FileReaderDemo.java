package com.rovger.io;

import java.io.*;

/**
 * @Description: TODO
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年09月26日 15:50
 */
public class FileReaderDemo {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/rovger/io/task"));
        /**
         * 两种循环读的方式
         */
        /*String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }*/
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            System.out.println(line);
        }

    }

}
