package com.demo.kafka.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelReaderUtil {

    public static List<String> getColumnData(String filePath) throws Exception{
        if(filePath.startsWith("classpath:")) {
            return getColumnData(Thread.currentThread().getContextClassLoader().getResourceAsStream(
                    filePath.substring("classpath:".length())), 0);
        }
        else {
            return getColumnData(filePath,0, 0);
        }
    }

    public static List<String> getColumnData(String filePath,int sheetIndex, int columnIndex) throws Exception{
        File file = new File(filePath);
        if(!file.exists()) {
            log.error("文件路径有误，找不到文件！ filePath = {}", filePath);
            return null;
        }
        FileInputStream fis = new FileInputStream(file);
        return getColumnData(fis, sheetIndex);
    }

    public static List<String> getColumnData(InputStream fis, int sheetIndex) throws IOException {
        List<String> columnData = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
        for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {
            XSSFRow row = sheet.getRow(j);
            if (row != null) {
                // getCell 获取单元格数据
                XSSFCell protocol = row.getCell(0);
                String cellData = protocol.toString();
                if (protocol != null) {
                    if (StringUtils.countMatches(cellData,"E")>0) {
                        columnData.add(protocol.getRawValue());
                    }else {
                        columnData.add(cellData);
                    }
                }
            }
        }
        return columnData;
    }
}
