package com.frg.springbatch.common;

import com.frg.springbatch.model.ProductDetail;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author FRG.
 */

public class ExcelWriter {

    String[] columns = {"Código Artículo", "Descripción", "Precio", "Linha", "Stock", "Image"};

    // Create a Workbook
    Workbook workbook = new XSSFWorkbook();

    public ExcelWriter() {
    }



    public Sheet addSheet() {
             // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances for various things like DataFormat,
           Hyperlink, RichTextString etc in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("Productos");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.AQUA.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Creating cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        return sheet;
    }

    public void updateExcel(Sheet sheet, List<ProductDetail> productDetails, int rowNum) throws IOException{
        // Create Other rows and cells with employees data
        //int rowNum = 1;
        for(ProductDetail product: productDetails) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(product.getCodArtigo());

            row.createCell(1)
                    .setCellValue(product.getDescricao());

            row.createCell(2)
                    .setCellValue(product.getValor());

            row.createCell(3)
                    .setCellValue(product.getLinha());

            row.createCell(4)
                    .setCellValue(product.getStock());

            row.createCell(5)
                    .setCellValue(product.getImage());
        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("catalogo-vauner.xlsx");
        workbook.write(fileOut);
        fileOut.close();

        workbook.close();
    }


    // Example to modify an existing excel file
    private void modifyExistingWorkbook() throws InvalidFormatException, IOException {
        // Obtain a workbook from the excel file
        Workbook workbook = WorkbookFactory.create(new File("existing-spreadsheet.xlsx"));

        // Get Sheet at index 0
        Sheet sheet = workbook.getSheetAt(0);

        // Get Row at index 1
        Row row = sheet.getRow(1);

        // Get the Cell at index 2 from the above row
        Cell cell = row.getCell(2);

        // Create the cell if it doesn't exist
        if (cell == null)
            cell = row.createCell(2);

        // Update the cell's value
        cell.setCellType(CellType.STRING);
        cell.setCellValue("Updated Value");

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("existing-spreadsheet.xlsx");
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();
    }
}

