package com.frg.springbatch.view;


import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ExcelView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        // getDate
        Date fecha = new Date();
        DateFormat Formato = new SimpleDateFormat("dd/mm/yyyy");


        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"Catalogo_"+Formato.format(fecha)+".xls\"");

        @SuppressWarnings("unchecked")
       // List<ProductDetail> users = (List<ProductDetail>) model.get("Products");

        // create excel xls sheet
        Sheet sheet = workbook.createSheet("ProductDetail CategoryDetail");
        sheet.setDefaultColumnWidth(30);

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);


        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("cod_artigo");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue("descricao");
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue("valor");
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue("Linha");
        header.getCell(3).setCellStyle(style);
        header.createCell(4).setCellValue("Stock");
        header.getCell(4).setCellStyle(style);
        header.createCell(5).setCellValue("image");
        header.getCell(5).setCellStyle(style);

        int rowCount = 1;

/*        for(ProductDetail user : users){
            Row userRow =  sheet.createRow(rowCount++);
            userRow.createCell(0).setCellValue(user.getCod_artigo());
            userRow.createCell(1).setCellValue(user.getDescricao());
            userRow.createCell(2).setCellValue(user.getValor());
            userRow.createCell(3).setCellValue(user.getLinha());
            userRow.createCell(4).setCellValue(user.getStock());
            userRow.createCell(5).setCellValue(user.getImage());

            }*/

    }

}
