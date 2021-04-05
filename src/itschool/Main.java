package itschool;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Main {
   public static void main(String[] args) {
      Workbook wb = null;
      String filename = "cmpl.xlsx";
      OPCPackage pkg = null;

      if (filename.endsWith("xlsx") || filename.endsWith("xls")) {
         boolean isXLSX = (filename.endsWith("xlsx"));
         ArrayList<Item> pricelist = new ArrayList<>();

         try {
            InputStream inp = new FileInputStream(filename);

            if (isXLSX) {
               // XSSFWorkbook, File (slower, but uses less memory)
//             pkg = OPCPackage.open(new File(filename));

               // XSSFWorkbook, InputStream, faster, but needs more memory
               pkg = OPCPackage.open(inp);
               wb = new XSSFWorkbook(pkg);
            } else {
               wb = new HSSFWorkbook(new POIFSFileSystem(inp));

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
                        //System.out.print(cell + "\t");
                    }
                    System.out.println();
*/
                  cell = row.getCell(0);
                  if (cell != null && cell.getCellType() == CellType.NUMERIC) {
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

            if (pkg != null) {
               pkg.close();
            }
         } catch (FileNotFoundException | InvalidFormatException e) {
            System.out.println(e.getLocalizedMessage());
         } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
         }

//         printList("\n-------------------- All Items --------------------\n", pricelist);


//         printList("\n-------------------- Items with keyword: \"USB\" --------------------\n", SearchByTitle(pricelist, "USB"));

//         printList("\n-------------------- Items with price under 30 --------------------\n", SearchByPriceLower(pricelist, 30));
//
         ArrayList<Item> Logitech_cheaper_200 = SearchByPriceLower(SearchByTitle(pricelist, "Logitech"), 200);
         printList("\n-------------------- Items with keyword: \"Logitech\" and price under 200 --------------------\n", Logitech_cheaper_200);


      } else {
         System.out.println("Given file is NOT Microsoft Excel file!");
      }
   }

   public static ArrayList<Item> SearchByPriceLower(ArrayList<Item> pricelist, double rosnichPrice) {
      ArrayList<Item> temp = new ArrayList<>();
      for (Item item : pricelist) {
         if (item.rosnichPrice < rosnichPrice) {
            //System.out.println(item);
            temp.add(item);
         }
      }
      return temp;
   }


   public static ArrayList<Item> SearchByTitle(ArrayList<Item> pricelist, String title) {
      ArrayList<Item> temp = new ArrayList<>();
      for (Item item : pricelist) {
         if (item.title.contains(title)) {
            //System.out.println(item);
            temp.add(item);
         }
      }
      return temp;
   }

   public static void printList(String header, ArrayList<Item> pricelist) {
      System.out.println(header);
      for (Item item : pricelist) {
         System.out.println(item);

      }
   }
}


