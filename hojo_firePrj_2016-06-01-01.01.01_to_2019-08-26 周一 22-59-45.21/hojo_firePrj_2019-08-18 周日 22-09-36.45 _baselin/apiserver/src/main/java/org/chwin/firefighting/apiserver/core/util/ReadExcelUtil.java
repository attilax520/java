package org.chwin.firefighting.apiserver.core.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class ReadExcelUtil {
    public static Map<Integer, Map<Integer,Object>> readExcelContentz(MultipartFile file) throws Exception{
        Map<Integer, Map<Integer, Object>> content = new HashMap<>();
        // 上传文件名
        Workbook wb = getWb(file);
        if (wb == null) {
            throw new Exception("Workbook对象为空！");
        }
        Sheet sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();

        Row row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            Map<Integer, Object> cellValue = new HashMap<>();
            while (j < colNum) {
                Object obj = getCellFormatValue(row.getCell(j));
                cellValue.put(j, obj);
                j++;
            }
            content.put(i, cellValue);

        }
        return content;
    }
    //根据Cell类型设置数据
    private static Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case NUMERIC:
                    cellvalue = cell.getNumericCellValue();
                    break;
                case STRING:
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                case FORMULA:
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }

    private static Workbook getWb(MultipartFile mf){
        String filepath = mf.getOriginalFilename();
        String ext = filepath.substring(filepath.lastIndexOf("."));
        Workbook wb = null;
        try {
            InputStream is = mf.getInputStream();
            if(".xls".equals(ext)){
                wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(ext)){
                wb = new XSSFWorkbook(is);
            }else{
                wb=null;
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return wb;
    }

}
