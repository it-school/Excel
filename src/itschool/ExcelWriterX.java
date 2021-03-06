package itschool;

import com.psec.excel.WriteExcel;

// http://gael-home.appspot.com/home/gael-home.htm
public class ExcelWriterX extends WriteExcel {
   WriteExcel wrtExcel;

   public void run(String filename) throws Exception {
      System.out.println(System.lineSeparator() + "Writing Excel-data stated to file: " + filename + System.lineSeparator());
      wrtExcel = WriteExcel.create(this, filename);
      wrtExcel.setNegativeFormat(true, "");

      writeSalesSheet();

      wrtExcel.close();
      wrtExcel = null; // такое присвоение позволяет сборщику мусора очистить память за неисползуемым объектом
      System.out.println(System.lineSeparator() + "Writing Excel-data completed to file: " + filename);
   }

   private void writeSalesSheet() throws Exception {
      String sheet = "Sales_sample";
      Area area = wrtExcel.createArea(sheet, 1, 1)
              .header("{4.#title}Sample Sales Report")
              .header("")
              .header("Month/Unit Sales/Avg. Price/Revenue", "#hdrBlue");

      String[] months = "January/February/March/April/May/June/July/August/September/October/November/December".split("/");
      double[] prices = new double[]{10.01, 11.02, 15.03, 9.04, 10.05, 17.06, 22.07, 23.08, 14.09, 12.10, 13.11, 18.12};
      int[] sales = new int[]{15, 61, 88, 23, -3, 54, 67, 53, 21, 13, 23, 33};
      int[] qtr = new int[]{0, 0, 1, 0, 0, 2, 0, 0, 3, 0, 0, 4};

      int qtrSales = 0;
      double qtrRev = 0.0;
      int totSales = 0;
      double totRev = 0.0;
      for (int i = 0, iMax = 12; i < iMax; i++) {
         area.addRow(String.format("{:R}%s/%d/%.2f/%.2f", months[i], sales[i], prices[i], prices[i] * sales[i]).split("/"), i + 1);
         qtrSales += sales[i];
         qtrRev += prices[i] * sales[i];
         totSales += sales[i];
         totRev += prices[i] * sales[i];
         if (qtr[i] != 0) {
            area.addRow(String.format("{:Rb}Q%d/%d/%.2f/%.2f", qtr[i], qtrSales, qtrRev / qtrSales, qtrRev).split("/"), "#qtr");
            qtrSales = 0;
            qtrRev = 0.0;
         }
      }
      area.addRow(new String[0]);
      area.addRow(String.format("{:Rb}TOTAL/%d/%.2f/%.2f", totSales, totRev / totSales, totRev).split("/"), "#TOT");
      area.writeArea().colWidth(-1, 3).addDataFilterLine();
   }
}