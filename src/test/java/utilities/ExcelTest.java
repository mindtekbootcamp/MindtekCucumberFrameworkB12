package utilities;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@SuppressWarnings("StringTemplateMigration")
public class ExcelTest {

    public static void main(String[] args) throws IOException {
//        String path = System.getProperty("user.dir")+"/src/test/resources/excel/TestData.xlsx";
//        try {
//            FileInputStream file = new FileInputStream(path);
//            Workbook workbook = new XSSFWorkbook(file);
//            Sheet sheet1 = workbook.getSheet("Sheet1");
//
//            System.out.println(sheet1.getRow(1).getCell(0).toString());
//            System.out.println(sheet1.getRow(1).getCell(1).toString());
//            System.out.println(sheet1.getRow(1).getCell(2).toString());
//            System.out.println(sheet1.getRow(1).getCell(3).toString());
//
//            sheet1.createRow(4).createCell(0).setCellValue("Adam");
//            sheet1.getRow(4).createCell(1).setCellValue("Lee");
//            sheet1.getRow(4).createCell(2).setCellValue("adam.lee@gmail.com");
//            sheet1.getRow(4).createCell(3).setCellValue("546 Sunny St");
//
//            FileOutputStream output = new FileOutputStream(path);
//            workbook.write(output);
//
//        } catch (FileNotFoundException e) {
//            System.out.println("Path to excel file is invalid or file is missing.");
//        } catch (IOException e) {
//            System.out.println("Failed to load/save excel file");
//        }

        ExcelUtils.openExcelFile("TestData", "Sheet1");
        System.out.println(ExcelUtils.getValue(4, 0));
        System.out.println(ExcelUtils.getValue(4, 1));
        ExcelUtils.setValue(5, 0, "Lauren");
        ExcelUtils.setValue(5, 1, "Brown");
    }
}
