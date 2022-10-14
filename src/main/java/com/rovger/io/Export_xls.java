//package com.rovger.io;
//
//import com.ebay.raptor.trackinghub.model.bean.AScanModel;
//import org.junit.Test;
//
//import java.io.FileOutputStream;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Description: TODO
// * @Author weijlu
// * @Date 2019/4/30 14:13
// */
//public class Export_xls {
//
//    @Test
//    public void test_1() {
//        List<AScanModel> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            AScanModel model = new AScanModel();
//            model.setTracking_id("" + i);
//            model.setDesc("999" + i);
//            model.setLocation("loca" + i);
//            list.add(model);
//        }
//        export_exls(list, "students.xls");
//    }
//
//    private void export_exls(List<AScanModel> data, String fileName) {
//        // 第一步，创建一个webbook，对应一个Excel文件
//        HSSFWorkbook wb = new HSSFWorkbook();
//        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
//        HSSFSheet sheet = wb.createSheet("a-scan info");
//        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
//        HSSFRow row = sheet.createRow((int) 0);
//        // 第四步，创建单元格，并设置值表头 设置表头居中
//        HSSFCellStyle style = wb.createCellStyle();
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
//
//        HSSFCell cell = row.createCell((short) 0);
//        cell.setCellValue("tracking_id");
//        cell.setCellStyle(style);
//        cell = row.createCell((short) 1);
//        cell.setCellValue("location");
//        cell.setCellStyle(style);
//        cell = row.createCell((short) 2);
//        cell.setCellValue("desc");
//        cell.setCellStyle(style);
//
//        for (int i = 0; i < data.size(); i++) {
//            row = sheet.createRow((int) i + 1);
//            AScanModel model = data.get(i);
//            // 第四步，创建单元格，并设置值
//            row.createCell((short) 0).setCellValue(model.getTracking_id());
//            row.createCell((short) 1).setCellValue(model.getLocation());
//            row.createCell((short) 2).setCellValue(model.getDesc());
//        }
//        // 第六步，将文件存到指定位置
//        try {
//            FileOutputStream fout = new FileOutputStream("C:\\Users\\weijlu\\Desktop\\New folder\\" + fileName);
//            wb.write(fout);
//            fout.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
