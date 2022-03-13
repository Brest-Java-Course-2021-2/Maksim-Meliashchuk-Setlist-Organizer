package com.epam.brest.service.excel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ExportExcelUtil {
    private final Logger logger = LogManager.getLogger(ExportExcelUtil.class);

    public static void exportToExcel (Class clazz, List objectsList) {

        Field[] fields = clazz.getDeclaredFields();
        Method[] allMethods = clazz.getDeclaredMethods();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(clazz.getSimpleName());

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setWrapText(true);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        headerStyle.setFont(font);

        for (int i = 0; i < fields.length; i++) {
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(fields[i].getName());
            headerCell.setCellStyle(headerStyle);
            sheet.autoSizeColumn(i, true);
        }

        CellStyle rowStyle = workbook.createCellStyle();
        rowStyle.setWrapText(true);

        for (int i = 0; i < objectsList.size(); i++) {
            Row row = sheet.createRow(1 + i);
            row.setHeight((short) 1000);

            for (int j = 0; j < fields.length; j++) {
                for (Method m : allMethods) {
                    String result;
                    try {
                        if (m.getName().contains("get") &&
                                m.getName().toLowerCase().contains(fields[j].getName().toLowerCase())) {
                            if (m.invoke(objectsList.get(i)) == null) continue;
                            result = m.invoke(objectsList.get(i)).toString();
                            Cell cell = row.createCell(j);
                            cell.setCellStyle(rowStyle);
                            cell.setCellValue(result);
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }

        }

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + clazz.getSimpleName() + ".xlsx";

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileLocation);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
