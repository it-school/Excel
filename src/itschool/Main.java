package itschool;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
   /**
    * Main method
    *
    * @param args argument
    *
    * @throws Exception if not correct XL
    */
   public static void main(String[] args) throws Exception {
      excelRead();
      excelWrite();
   }

   //  https://poi.apache.org/apidocs/index.html

   /**
    * Method to read data from Excel file
    */
   private static void excelRead() {
      long start = System.currentTimeMillis();

      Workbook wb = null;
      String filename = "cmpl.xls";
      OPCPackage pkg = null;
      List<Item> pricelist = new ArrayList<>();

      if (filename.toLowerCase().endsWith("xlsx") || filename.toLowerCase().endsWith("xls")) {
         boolean isXLSX = (filename.endsWith("xlsx"));

         try {
            InputStream inp = new FileInputStream(filename);

            if (isXLSX) {
               // XSSFWorkbook, File (slower, but uses less memory)
               pkg = OPCPackage.open(new File(filename));
               // XSSFWorkbook, InputStream, faster, but needs more memory
               wb = new XSSFWorkbook(OPCPackage.open(inp));
            } else {
               POIFSFileSystem is = new POIFSFileSystem(inp);
               wb = new HSSFWorkbook(is);
               /*
                  ExcelExtractor extractor = new ExcelExtractor(wb);
                  extractor.setFormulasNotResults(true);
                  extractor.setIncludeSheetNames(false);
                  String text = extractor.getText();
                  System.out.println(text);
               */
            }

            Item item = new Item();
            Cell cell;
            int id;
            String title;
            String sklad;
            double rosnichPrice;
            double optPrice;
            double dilPrice;
            double gar;

            for (Sheet sheet : wb) {
               for (Row row : sheet) {
/*
                    for (Cell cell : row)
                    {
                        System.out.print(cell + "\t");
                    }
                    System.out.println();
*/
                  cell = row.getCell(0);
                  if (cell == null) {
                     title = row.getCell(1).getStringCellValue();
                     System.out.println(title);
                  } else if (cell.getCellType() == CellType.NUMERIC) {
                     id = (int) cell.getNumericCellValue();
                     title = row.getCell(1).getStringCellValue();
                     sklad = row.getCell(2).getStringCellValue();
                     rosnichPrice = row.getCell(3).getNumericCellValue();
                     optPrice = row.getCell(4).getNumericCellValue();
                     dilPrice = row.getCell(5).getNumericCellValue();
                     gar = row.getCell(6).getNumericCellValue();
                     item = new Item(id, title, sklad, rosnichPrice, optPrice, dilPrice, gar);
                     pricelist.add(item);
                     // System.out.println(item.toString());
                  }
               }
            }
            wb.close();

            if (pkg != null) pkg.close();
         } catch (InvalidFormatException | IOException e) {
            System.out.println(e.getLocalizedMessage());
         }

         long finish = System.currentTimeMillis();
         System.out.println(finish - start + " ms\n");

         System.out.println("\n\n");

         Items priceListFull = new Items(pricelist);
         System.out.println(priceListFull);

         Items LogitechList = priceListFull.SearchByTitle("Logitech");
         System.out.println(LogitechList);
         System.out.println(LogitechList.list.size());

         Items sublist2 = priceListFull.SearchByPriceLowerThan(20);
         System.out.println(sublist2);

         Items sublist3 = LogitechList.SearchByPriceLowerThan(400).SearchByTitle("USB");
         System.out.println(sublist3);
         sublist3.sort(Item.byTitleAscending);
         System.out.println("\nSort by title: \n" + sublist3);

         sublist3.sort(Item.byIDDescending);
         System.out.println("\nSort by id descending: \n" + sublist3);

         System.out.println("\n\nSort by price ASC\n");
         System.out.println(Items.sortCopy(sublist2, Item.byPriceDesc)); // Sort without changing source list data
         System.out.println(sublist2);
         sublist2.sort(Item.byPriceAsc);  // Sort with changing source list data
         System.out.println(sublist2);

         System.out.println(sublist2.SearchByTitle("BNC"));
         System.out.println("Sort by price DESC");
         sublist2.sort(Item.byPriceDesc);
         System.out.println(sublist2);

         Items Logitech_cheaper_200 = priceListFull.SearchByPriceLowerThan(200).SearchByTitle("Transcend");
         System.out.println(Logitech_cheaper_200);

      } else {
         System.out.println("Given file is NOT Microsoft Excel file!");
      }
   }

   /**
    * Method to write data to Excel file
    *
    * @throws Exception if error occurs
    */
   private static void excelWrite() throws Exception {
      ExcelWriterX writeXLS = new ExcelWriterX();
      writeXLS.run("test.xlsx");
   }
}