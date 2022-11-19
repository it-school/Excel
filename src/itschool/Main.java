// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com

package itschool;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
   public static void main(final String[] args) throws Exception {
      excelRead("cmpl.xls");
      excelWrite("test.xlsx");
   }

   //  https://poi.apache.org/apidocs/index.html

   /**
    * Example of reading data from Excel file
    */
   private static void excelRead(String fileName) {
      final long start = System.currentTimeMillis();  // benchmarking started
      fileName = fileName.strip().toLowerCase();

      if (fileName.endsWith("xlsx") || fileName.endsWith("xls")) {
         final List<Item> priceList = new ArrayList<>();

         try {
            final InputStream inputStream = Files.newInputStream(Paths.get(fileName));
            final Workbook workbook;

            if (fileName.endsWith("xlsx")) {
               OPCPackage opcPackage = OPCPackage.open(inputStream);    // using InputStream is faster, but needs LESS RAM
               // opcPackage = OPCPackage.open(new File(fileName));     // using File        is slower, but uses MORE RAM
               workbook = new XSSFWorkbook(opcPackage);
               opcPackage.close();
            } else {
               POIFSFileSystem poifsFileSystem = new POIFSFileSystem(inputStream);
               workbook = new HSSFWorkbook(poifsFileSystem);
               poifsFileSystem.close();
               /*
                  ExcelExtractor extractor = new ExcelExtractor(workbook);
                  extractor.setFormulasNotResults(true);
                  extractor.setIncludeSheetNames(false);
                  String text = extractor.getText();
                  System.out.println(text);
               */
            }

            Cell cell;
            int id;
            String title;
            String warehouse;
            double retailPrice;
            double wholesalePrice;
            double dealerPrice;
            double guarantee;

            for (final Sheet sheet : workbook) {
               for (final Row row : sheet) {
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
                     warehouse = row.getCell(2).getStringCellValue();
                     retailPrice = row.getCell(3).getNumericCellValue();
                     wholesalePrice = row.getCell(4).getNumericCellValue();
                     dealerPrice = row.getCell(5).getNumericCellValue();
                     guarantee = row.getCell(6).getNumericCellValue();
                     priceList.add(new Item(id, title, warehouse, retailPrice, wholesalePrice, dealerPrice, guarantee));
                  }
               }
            }
            workbook.close();
            inputStream.close();
         } catch (InvalidFormatException | IOException e) {
            System.out.println(e.getLocalizedMessage());
         }

         final long finish = System.currentTimeMillis();  // benchmarking is finished
         System.out.println(finish - start + " ms\n\n\n");


         // Received data manipulation

         final Items priceListFull = new Items(priceList);
         System.out.println(priceListFull);

         final Items logitechOnly = priceListFull.searchByTitle("Logitech");
         System.out.println(logitechOnly);
         System.out.println(logitechOnly.getList().size());

         final Items cheapest = priceListFull.searchByPriceLower(20);
         System.out.println(cheapest);

         final Items cheapUSB = logitechOnly.searchByPriceLower(400).searchByTitle("USB");
         System.out.println(cheapUSB);
         cheapUSB.sort(Item.byTitleAscending);
         System.out.println("\nSort by title: \n" + cheapUSB);

         cheapUSB.sort(Item.byIDDescending);
         System.out.println("\nSort by id descending: \n" + cheapUSB);

         System.out.println("\n\nSort by price ASC\n");
         System.out.println(Items.sortCopy(cheapest, Item.byRetailPriceDesc)); // Sort without changing source list data
         System.out.println(cheapest);
         cheapest.sort(Item.byRetailPriceAsc);  // Sort with changing source list data
         System.out.println(cheapest);

         System.out.println(cheapest.searchByTitle("BNC"));
         System.out.println("Sort by price DESC");
         cheapest.sort(Item.byRetailPriceDesc);
         System.out.println(cheapest);

         final Items logitechCheaper200 = priceListFull.searchByPriceLower(200).searchByTitle("Transcend");
         System.out.println(logitechCheaper200);

      } else {
         System.out.println("Given file is NOT Microsoft Excel file!");
      }
   }

   /**
    * Example of writing data to created Excel file (XLSX)
    *
    * @throws Exception if error occurs
    */
   private static void excelWrite(final String fileName) throws Exception {
      final ExcelWriterX writeXLS = new ExcelWriterX();
      writeXLS.run(fileName);
   }
}