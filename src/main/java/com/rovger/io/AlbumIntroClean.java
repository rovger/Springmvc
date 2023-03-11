package com.rovger.io;

import com.rovger.utils.RegularExp;

import java.io.*;

/**
 * @author weijlu
 * @version 1.0
 * @description 专辑简介清洗
 * @date 2022/12/21 14:52:05
 */
public class AlbumIntroClean {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/rovger/io/intro.txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/com/rovger/io/output.txt"));

        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(RegularExp.contentIntro(line));
            writer.newLine();
        }
        writer.flush();
    }

}
