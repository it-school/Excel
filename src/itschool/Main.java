package itschool;

// https://poi.apache.org/components/spreadsheet/quick-guide.html
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

public class Main
{

    public static void main(String[] args)
    {
        Workbook wb = null;
        String filename = "a:\\pricefull.xls";
        OPCPackage pkg = null;
        boolean isXLSX = (filename.contains("xlsx") ? true : false);
        ArrayList<Item> pricelist = new ArrayList<>();

        try
        {
            InputStream inp = new FileInputStream(filename);

            if (isXLSX)
            {
                // XSSFWorkbook, File (slower, but uses less memory)
//                pkg = OPCPackage.open(new File(filename));

                // XSSFWorkbook, InputStream, faster, but needs more memory
                pkg = OPCPackage.open(inp);

                wb = new XSSFWorkbook(pkg);
            }
            else
            {
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
            double price;
            for (Sheet sheet : wb)
            {
                for (Row row : sheet)
                {
                    /*
                    for (Cell cell : row)
                    {
                        //System.out.print(cell + "\t");
                    }
                    System.out.println();
*/
                    cell = row.getCell(0);
                    if (cell != null && cell.getCellType() == CellType.NUMERIC)
                    {
                        id = (int)cell.getNumericCellValue();
                        title = row.getCell(1).getStringCellValue();
                        price = row.getCell(2).getNumericCellValue();
                        item = new Item(id, title, price);
                        item.tryToConvert();
                        pricelist.add(item);
                        System.out.println(item.toString());
                    }
                }
            }
            wb.close();

            if (pkg != null)
            {
                pkg.close();
            }

        }
        catch (FileNotFoundException | InvalidFormatException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
