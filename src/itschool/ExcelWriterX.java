package itschool;

import com.psec.excel.WriteExcel;

import java.awt.*;
import java.io.File;

// http://gael-home.appspot.com/home/gael-home.htm

/**
 * Example of creating new XLSX file with data
 */
public class ExcelWriterX extends WriteExcel {
   private final int MONTHS = 12;
   private WriteExcel wrtExcel;

   public void run(final String filename) throws Exception {
      System.out.println(System.lineSeparator() + "Запись данных в файл формата XLSX: " + filename + System.lineSeparator());

      wrtExcel = WriteExcel.create(this, filename);
      wrtExcel.setNegativeFormat(true, "");
      writeSalesSheet();

      wrtExcel.close();
      wrtExcel = null; // такое присвоение позволяет сборщику мусора очистить память за неиспользуемым объектом
      System.out.println(System.lineSeparator() + "Запись данных успешно завершена");

      Desktop.getDesktop().open(new File(filename));
   }

   private void writeSalesSheet() throws Exception {
      final String sheetTitle = "Годовой отчёт";
      final Area area = wrtExcel.createArea(sheetTitle, 1, 1).header("{4.#title}Отчёт по продажам").header("").header("Месяц/Кол-во/Ср.цена/Итог", "#hdrBlue");

      final String[] months = "Январь/Февраль/Март/Апрель/Май/Июнь/Июль/Август/Сентябрь/Октябрь/Ноябрь/Декабрь".split("/");
      final double[] prices = {10.01, 11.02, 15.03, 9.04, 10.05, 17.06, 22.07, 23.08, 14.09, 12.10, 13.11, 18.12};
      final int[] sales = {15, 61, 88, 23, -3, 54, 67, 53, 21, 13, 23, 33};
      final int[] quarter = {0, 0, 1, 0, 0, 2, 0, 0, 3, 0, 0, 4};

      int quarterSales = 0;
      double quarterRevenue = 0.0;
      int totalSales = 0;
      double totalRevenue = 0.0;
      for (int i = 0; i < MONTHS; i++) {
         area.addRow(String.format("{:R}%s/%d/%.2f/%.2f", months[i], sales[i], prices[i], prices[i] * sales[i]).split("/"), i + 1);
         quarterSales += sales[i];
         quarterRevenue += prices[i] * sales[i];
         totalSales += sales[i];
         totalRevenue += prices[i] * sales[i];
         if (quarter[i] > 0) {
            area.addRow(String.format("{:Rb}Квартал %d/%d/%.2f/%.2f", quarter[i], quarterSales, quarterRevenue / quarterSales, quarterRevenue).split("/"), "#quarter");
            quarterSales = 0;
            quarterRevenue = 0.0;
         }
      }
      area.addRow(new String[0]);
      area.addRow(String.format("{:Rb}ИТОГО/%d/%.2f/%.2f", totalSales, totalRevenue / totalSales, totalRevenue).split("/"), "#TOT");
      area.writeArea().colWidth(-1, 3).addDataFilterLine();
   }
}