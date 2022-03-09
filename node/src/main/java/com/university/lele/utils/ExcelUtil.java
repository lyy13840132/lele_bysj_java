package com.university.lele.utils;


import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {


    public static final String EMPTY = "";
    private static final String POINT = ".";
    //private NumberFormat numberFormat;
    String[] sheetName = { "成绩详细记录表" };

    /**
     * 获得path的后缀名
     *
     * @param path 文件路径
     * @return 路径的后缀名
     */
    public static String getPostfix(String path) {
        if (path == null || EMPTY.equals(path.trim())) {
            return EMPTY;
        }
        if (path.contains(POINT)) {
            return path.substring(path.lastIndexOf(POINT) + 1, path.length());
        }
        return EMPTY;
    }

    /**
     * 解析xls和xlsx不兼容问题
     *
     * @param pfs
     * @param workbook
     * @param file
     * @return
     */
    public static Workbook getWorkBook(POIFSFileSystem pfs, Workbook workbook, MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        if (filename.endsWith("xls")) {
            pfs = new POIFSFileSystem(file.getInputStream());
            //创建工作簿
            //
            workbook = new HSSFWorkbook(pfs);
            return workbook;
        } else if (filename.endsWith("xlsx")) {
            try {
                workbook = new XSSFWorkbook(file.getInputStream());
                return workbook;
            } catch (IOException e) {
                return null;
            }
        } else {
            return null;
        }
    }


    // 创建excel文件
    public void writeEXCEL(String excelPath, List<?> values) throws IOException {
        // TODO 自动生成的方法存根
        File f = new File(excelPath);
        if (!f.exists()) {
            creatEXcel(excelPath);
        }
        writeAppend(excelPath, values);
    }

    public void creatEXcel(String excelPath) {
        File file = new File(excelPath);
        // 定义表头
        // 创建excel工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建工作表sheet
        String[] t = { "学号", "姓名", "班级", "学生互评成绩", "正常打卡", "迟到打卡", "外勤打卡","外勤迟到打卡","是否由相似度较高的文档记录","最终成绩" };
        List<String[]> titles = new ArrayList<>();
        titles.add(t);
        HSSFSheet sheet;
        sheet = workbook.createSheet(sheetName[0]);
        // 创建第一行
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;

        // 插入第一行数据的表头
        for (int j = 0; j < titles.get(0).length; j++) {
            cell = row.createCell(j);
            cell.setCellValue(titles.get(0)[j]);
        }
        try {
            file.createNewFile();
            // 将excel写入
            FileOutputStream stream = FileUtils.openOutputStream(file); // POI 包的工具类
            workbook.write(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAppend(String excelPath, List<?> values) throws IOException {
        FileInputStream fs = new FileInputStream((excelPath)); // 获取head.xls
        POIFSFileSystem ps = new POIFSFileSystem(fs); // 使用POI提供的方法得到excel的信息
        HSSFWorkbook wb = new HSSFWorkbook(ps);
        HSSFSheet sheet1=wb.getSheetAt(0);
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
        HSSFRow row1 = sheet1.getRow(0); // 获取第一行（excel中的行默认从0开始，所以这就是为什么，一个excel必须有字段列头），即，字段列头，便于赋值
        FileOutputStream out = new FileOutputStream((excelPath)); // 向head.xls中写数据
        row1 = sheet1.createRow((short) (sheet1.getLastRowNum() + 1)); // 在现有行号后追加数据
        for (int i = 0; i < values.size(); i++) {
            if (i < 3) {
                row1.createCell(i).setCellValue(values.get(i).toString());
                // 设置第一个（从0开始）单元格的数据
            } else {
                row1.createCell(i).setCellValue(values.get(i).toString());
                row1.getCell(i).setCellStyle(cellStyle);
            }
//
        }
        out.flush();
        wb.write(out);
        out.close();
    }

}
