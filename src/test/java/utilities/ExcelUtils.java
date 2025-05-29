package utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    private static String path;
    private static Workbook workbook;
    private static Sheet sheet;

    /**
     * This method opens Excel files.
     * @param fileName
     * @param sheetName
     */
    public static void openExcelFile(String fileName, String sheetName){
        path = System.getProperty("user.dir")+"/src/test/resources/excel/"+fileName+".xlsx";
        try {
            FileInputStream file = new FileInputStream(path);
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet(sheetName);
        } catch (FileNotFoundException e) {
            System.out.println("Path to excel file is invalid or file is missing.");
        } catch (IOException e) {
            System.out.println("Failed to load/save excel file");
        }
    }

    /**
     * This method returns a cell value by using the row and cell indexes.
     * @param row
     * @param cell
     * @return
     */
    public static String getValue(int row, int cell){
        return sheet.getRow(row).getCell(cell).toString();
    }

    /**
     * This method sets cell value by using the row and cell indexes.
     * @param row
     * @param cell
     * @param cellValue
     */
    public static void setValue(int row, int cell, String cellValue) throws IOException {

        int numberOfRows = sheet.getPhysicalNumberOfRows();
        Row row1;
        if (row>=numberOfRows){
            row1 = sheet.createRow(row);
        }else{
            row1 = sheet.getRow(row);
        }

        int numberOfCells = row1.getPhysicalNumberOfCells();
        Cell cell1;
        if (cell>=numberOfCells){
            cell1 = row1.createCell(cell);
        }else{
            cell1 = row1.getCell(cell);
        }
        cell1.setCellValue(cellValue);

        FileOutputStream output = null;
        try {
            output = new FileOutputStream(path);
            workbook.write(output);
        } catch (FileNotFoundException e) {
            System.out.println("Failed to save Excel file");
        } catch (IOException e) {
            System.out.println("Failed to add output to file");
        }finally {
            assert output != null;
            output.close();
        }
    }
}
