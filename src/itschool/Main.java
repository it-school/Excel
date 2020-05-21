package itschool;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		excelRead();
		writeExcel();
		return;
	}

	private static void writeExcel() throws Exception
	{
		ExcelWriterX writexls = new ExcelWriterX();
		writexls.run("test.xlsx");
	}

	//  https://poi.apache.org/apidocs/index.html
	private static void excelRead()
	{
		long start = System.currentTimeMillis();

		Workbook wb = null;
		String filename = "cmpl.xls";
		OPCPackage pkg = null;
		ArrayList<Item> pricelist = new ArrayList<>();

		if (filename.endsWith("xlsx") || filename.endsWith("xls")) {
			boolean isXLSX = (filename.endsWith("xlsx"));

			try {
				InputStream inp = new FileInputStream(filename);

				if (isXLSX) {
					// XSSFWorkbook, File (slower, but uses less memory)
					pkg = OPCPackage.open(new File(filename));

					// XSSFWorkbook, InputStream, faster, but needs more memory
					wb = new XSSFWorkbook(OPCPackage.open(inp));
				}
				else {
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
						if (cell == null) {
							title = row.getCell(1).getStringCellValue();
							System.out.println(title);
						}
						else if (cell != null && cell.getCellType() == CellType.NUMERIC) {
							id = (int) cell.getNumericCellValue();
							title = row.getCell(1).getStringCellValue();
							sklad = row.getCell(2).getStringCellValue();
							rosnichPrice = row.getCell(3).getNumericCellValue();
							optPrice = row.getCell(4).getNumericCellValue();
							dilPrice = row.getCell(5).getNumericCellValue();
							gar = row.getCell(6).getNumericCellValue();
							item = new Item(id, title, sklad, rosnichPrice, optPrice, dilPrice, gar);
							// item.tryToConvert();
							pricelist.add(item);
							//System.out.println(item.toString());
						}
					}
				}
				wb.close();

                /*for (Item item1 : pricelist) {
                    System.out.println(item1);
                }*/

				if (pkg != null) {
					pkg.close();
				}
			}
			catch (FileNotFoundException | InvalidFormatException e) {
				System.out.println(e.getLocalizedMessage());
			}
			catch (IOException e) {
				System.out.println(e.getLocalizedMessage());
			}

			long finish = System.currentTimeMillis();
			System.out.println(finish - start);

			System.out.println("\n\n");

			Items pricelistFull = new Items(pricelist);
			Items USBlist = pricelistFull.SearchByTitle("USB");
			System.out.println(USBlist);
			System.out.println(USBlist.list.size());

			Items sublist2 = pricelistFull.SearchByPriceLowerThan(20);
			sublist2.sort(Item.byPriceAsc);
			System.out.println(sublist2);
			sublist2.sort(Item.byPriceDesc);
			System.out.println(sublist2);

			Items Logitech_cheaper_200 = pricelistFull.SearchByTitle("Logitech").SearchByPriceLowerThan(200);
			System.out.println(Logitech_cheaper_200);
		}
		else {
			System.out.println("Given file is NOT Microsoft Excel file!");
		}
	}
}