package com.frg.springbatch.common;

import com.frg.springbatch.model.ProductDetail;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author FRG.
 */

@Service
public class ExcelWriter {

    String[] columns = {"Código Artículo", "Descripción", "Precio", "Linha", "Stock", "Image"};

    /** Atributo workbook. */
    private XSSFWorkbook workbook;

    /**  Fila por la que vamos escribiendo el el fichero de salida. */
    private int posFila;

    /** Atributo hojaTratada. */
    private XSSFSheet hojaTratada;

    /** Atributo filaTratada. */
    private XSSFRow filaTratada;

    /** Indica la columna en la que se escribira el siguiente error. */
    private int posColumna;


    public void createBook(String nameSheet){
        posFila = 0;
        posColumna = 0;

        // Create a Workbook
        workbook = new XSSFWorkbook();

        /* CreationHelper helps us create instances for various things like DataFormat,
           Hyperlink, RichTextString etc in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

        if (!StringUtils.isEmpty(nameSheet)){
            hojaTratada = workbook.createSheet(nameSheet);
        } else {
            hojaTratada = workbook.createSheet();
        }

        insertHeader(hojaTratada);
        //filaTratada = hojaTratada.createRow(0);
    }

    public void createBook(){
        posFila = 0;
        posColumna = 0;

        // Create a Workbook
        workbook = new XSSFWorkbook();

        /* CreationHelper helps us create instances for various things like DataFormat,
           Hyperlink, RichTextString etc in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

    }



    public void addSheet(String nameSheet){

        // Create a Sheet
        hojaTratada = workbook.createSheet(nameSheet);
        // Insert Header
        insertHeader(hojaTratada);

    }

    private void insertHeader(Sheet sheet) {
        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLUE_GREY.getIndex());


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
    }

    public void addRow(Sheet sheet, List<ProductDetail> productDetails) {
        // Create Other rows and cells with employees data
        int rowNum = 1;
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
    }


    public void closeBook(Sheet sheet) throws IOException{
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




    /**
     * Perapara para tratar la pagina indicada.
     *
     * @param idHoja indica el id de la hoja
     */
    public void abrirHoja(String idHoja) {
        hojaTratada = getHoja(idHoja);
        posFila = 0;
        abrirFila(0);
        if (idHoja != null) {
            //info("[abrirLibro] Abierta la hoja " + idHoja);
        }
    }

    /**
     * Prepara para tratar la fila de posicion indicada.
     *
     * @param numFila indica el numero de fila
     */
    public void abrirFila(int numFila) {
        filaTratada = getFila(numFila);
        posColumna = 0;
    }

    /**
     * Obtiene el libro.
     *
     * @return Devuelve el libro Excel que se esta tratando.
     */
    public XSSFWorkbook getLibro() {
        return workbook;
    }

    /**
     * Obtiene el hoja.
     *
     * @return Devuelve la hoja del Excel que se esta tratando.
     */
    public XSSFSheet getHoja() {
        return hojaTratada;
    }

    /**
     * Devuelve la hoja en la posicion indicada por parametro.
     *
     * @param pos indica la posicion
     * @return hoja en la posicion indicada
     */
    public String getHojaName(int pos) {
        return workbook.getSheetAt(pos).getSheetName();
    }

    /**
     * Devuelve el numero de hojas de datos que contiene la excel.
     *
     * @return numero de hojas de datos
     */
    public int getNumHojas() {
        return workbook.getNumberOfSheets();
    }

    /**
     * Devuelve la hoja indidada por idHoja, que puede ser posicion o nombre.
     *
     * @param idHoja indica el id de la hoja
     * @return Devuelve la hoja indicada por idHoja.
     */
    public XSSFSheet getHoja(String idHoja) {
        XSSFSheet hoja = null;
        if (idHoja != null && !"".equals(idHoja.trim())) {
            try {
                int posHoja = Integer.parseInt(idHoja);
                hoja = workbook.getSheetAt(posHoja);
            } catch (NumberFormatException e) {
                hoja = workbook.getSheet(idHoja);
            }
        } else {
            hoja = workbook.getSheetAt(0);
        }
        return hoja;
    }

    /**
     * Obtiene el fila.
     *
     * @return Devuelve la fila del Excel que se esta tratando.
     */
    public XSSFRow getFila() {
        return filaTratada;
    }

    /**
     * Devuelve la fila del Excel en la posicion indicada.
     *
     * @param numFila indica el numero de fila
     * @return Devuelve la fila numero numFila
     */
    public XSSFRow getFila(int numFila) {
        return hojaTratada.getRow(numFila);
    }

    /**
     * Obtiene el celda.
     *
     * @return Devuelve la celda del Excel que se esta tratando.
     */
    public XSSFCell getCelda() {
        return filaTratada.getCell(posColumna);
    }

    /**
     * Obtiene el celda.
     *
     * @param fila    Fila en la que se encuentra la celda que se quiere recuperar.
     * @param columna Columna en la que se encuentra la celda que se quiere
     *                recuperar.
     * @return Devuelve la celda del Excel indicada por su posicion.
     */
    public XSSFCell getCelda(int fila, int columna) {
        if (hojaTratada.getRow(fila) != null) {
            return hojaTratada.getRow(fila).getCell(columna);
        }
        return null;
    }

    /**
     * Devuelve el numero de filas que tiene la hoja indicada, o -1 si no
     * encuentra la hoja.
     *
     * @param hoja indica la hoja
     * @return Devuelve el numero de filas de la hoja excel.
     */
    public int getNumFilasHoja(String hoja) {
        XSSFSheet hojaExcel = getHoja(hoja);
        if (hojaExcel != null) {
            return hojaExcel.getLastRowNum();
        }
        return -1;
    }

    /**
     * Devuelve el numero de filas que tiene la hoja que se esta tratando en
     * este momento.
     *
     * @return Devuelve el numero de filas de la hoja activa.
     */
    public int getNumFilasHoja() {
        return hojaTratada.getLastRowNum();
    }

}

