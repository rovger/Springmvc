package com.rovger.io;

import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * @Description: 文件流的创建，读取，文件批量移动。。。
 * @Author weijlu
 * @Date 2019/3/1 11:08
 */
public class FileOperations {

    private static final String src_path = "dir/";
    private static final String dest_path = "dest/";

    public static void main(String[] args) {

        ioWriter();

//        ioReader();

//        copyOrMoveDir();

    }

    /**
     * 写文件流 --> BufferedWriter(OutputStreamWriter osw)
     * 注意：关闭流的规则及顺序
     * 1. 要么只关闭最高层流，即释放所有相关资源
     * 2. 要么由外至内的顺序关闭流
     */
    private static void ioWriter() {
        BufferedWriter bw = null;
        try {
//            File file = new File(src_path + "input.txt");
//            if (!file.exists()) {
//                file.getParentFile().mkdir();
//                file.createNewFile();
//            }
//            OutputStream outputStream = new FileOutputStream(file);
//            bw = new BufferedWriter(new OutputStreamWriter(outputStream));
            bw = new BufferedWriter(new FileWriter(src_path + "input.txt"));
            bw.write("Hi exception,");
            bw.newLine();
            bw.write("\t Exception in thread \"main\" java.io.IOException: Stream closed \n");
            bw.write("\t at sun.nio.cs.StreamEncoder.ensureOpen(StreamEncoder.java:26) \n");
            bw.write("\t at sun.nio.cs.StreamEncoder.write(StreamEncoder.java:99)");
            bw.newLine();
            bw.write("Ending.......");

            bw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {//直接关闭最高层流，那么它引用的其他流资源也会被释放
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读文件流
     */
    private static void ioReader() {
        BufferedReader br = null;
        try {
//            File file = new File(src_path + "input.txt");
//            InputStream inputStream = new FileInputStream(file);
//            InputStreamReader isr = new InputStreamReader(inputStream);
            br = new BufferedReader(new FileReader(src_path + "input.txt"));
            String lineTxt;
            while ((lineTxt = br.readLine()) != null) {
                System.out.println(lineTxt);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 复制/移动目录中的文件
     * 1. 当dest目录已经存在，src目录下所有文件到dest目录
     * 2. 当dest目录不存在，则直接将src目录move到dest下，然后删除souce目录
     */
    private static void copyOrMoveDir() {

        try {
            File destFile = new File(dest_path);
            File srcFile = new File(src_path);
            if (!srcFile.exists()) return;
            if (destFile.exists()) {
                FileUtils.copyDirectory(srcFile, destFile);
                FileUtils.deleteDirectory(srcFile);
            } else {
                FileUtils.moveDirectory(srcFile, destFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
