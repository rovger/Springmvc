package machineLearning;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by weijlu on 2017/11/27.
 */
public class TrainingDataGenerate {

    public static void main(String[] args) {

        int TRAIN_DATA_COUNT = 10000;
        Object[] header = {"PTS", "REBS", "ASS", "STS", "BLS", "POSITION"};
        List<Object> headerList = Arrays.asList(header);

        List<List<Object>> bodyList = new ArrayList<>();
        Random random = new Random();
        for (int i=0; i<TRAIN_DATA_COUNT/5; i++) {
            // 1号位数据
            List<Object> oneList = new ArrayList<>();
            oneList.add(random.nextInt(15));
            oneList.add(random.nextInt(5));
            oneList.add(random.nextInt(10) + 5);
            oneList.add(random.nextInt(5));
            oneList.add(random.nextInt(2));
            oneList.add(1);
            bodyList.add(oneList);

            // 2号位数据
            List<Object> twoList = new ArrayList<>();
            twoList.add(random.nextInt(20) + 10);
            twoList.add(random.nextInt(5));
            twoList.add(random.nextInt(10));
            twoList.add(random.nextInt(5));
            twoList.add(random.nextInt(2));
            twoList.add(2);
            bodyList.add(twoList);

            // 3号位数据
            List<Object> threeList = new ArrayList<>();
            threeList.add(random.nextInt(25) + 10);
            threeList.add(random.nextInt(5) + 5);
            threeList.add(random.nextInt(5));
            threeList.add(random.nextInt(3));
            threeList.add(random.nextInt(3));
            threeList.add(3);
            bodyList.add(threeList);

            // 4号位数据
            List<Object> fourList = new ArrayList<>();
            fourList.add(random.nextInt(15));
            fourList.add(random.nextInt(10));
            fourList.add(random.nextInt(5));
            fourList.add(random.nextInt(3));
            fourList.add(random.nextInt(5));
            fourList.add(4);
            bodyList.add(fourList);

            // 5号位数据
            List<Object> fiveList = new ArrayList<>();
            fiveList.add(random.nextInt(15));
            fiveList.add(random.nextInt(10) + 10);
            fiveList.add(random.nextInt(5));
            fiveList.add(random.nextInt(2));
            fiveList.add(random.nextInt(5) + 3);
            fiveList.add(5);
            bodyList.add(fiveList);
        }
        String fileName = "C:\\Users\\weijlu\\Desktop\\train_data_nba.csv";
        File csvFile = null;
        BufferedWriter csvWriter = null;
        try {
            csvFile = new File(fileName);
            csvFile.createNewFile();

            csvWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);
            StringBuffer sb = new StringBuffer();
            sb.append(TRAIN_DATA_COUNT).append(",").append(header.length - 1);
            csvWriter.write(sb.toString());
            csvWriter.newLine();

            //写入文件头部
            writeRow(headerList, csvWriter);
            csvWriter.newLine();

            //写入文件内容
            for (List<Object> row : bodyList) {
                writeRow(row, csvWriter);
                csvWriter.newLine();
            }
            csvWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写一行数据
     * @param row
     * @param csvWriter
     */
    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        for (Object data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append(data).append(",").toString();
            csvWriter.write(rowStr);
        }
    }
}
