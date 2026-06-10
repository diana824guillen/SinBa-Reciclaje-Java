package com.sinba.util;

import com.sinba.model.Reciclaje;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {
    public static void exportarReciclajes(List<Reciclaje> reciclajes, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reciclajes");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Material");
        header.createCell(1).setCellValue("Cantidad (kg)");
        header.createCell(2).setCellValue("Puntos");
        header.createCell(3).setCellValue("Ubicación");
        header.createCell(4).setCellValue("Fecha");

        int rowNum = 1;
        for (Reciclaje r : reciclajes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(r.getMaterial());
            row.createCell(1).setCellValue(r.getCantidad());
            row.createCell(2).setCellValue(r.getPuntos());
            row.createCell(3).setCellValue(r.getUbicacion());
            row.createCell(4).setCellValue(r.getFecha());
        }

        FileOutputStream fileOut = new FileOutputStream(filePath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }
}