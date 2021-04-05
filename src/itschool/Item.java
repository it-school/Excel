package itschool;

import java.util.Currency;
import java.util.Locale;

public class Item {
   int id;
   String title;
   String sklad;
   double rosnichPrice;
   double optPrice;
   double dilPrice;
   double gar;


   public Item() {
      this.id = -1;
      this.gar = -1;
      this.dilPrice = -1;
      this.optPrice = -1;
      this.rosnichPrice = -1;
      this.title = "";
      this.sklad = "";
   }

   public Item(int id, String title, String sklad, double rosnichPrice, double optPrice, double dilPrice, double gar) {
      this.id = id;
      this.sklad = sklad;
      this.rosnichPrice = rosnichPrice;
      this.optPrice = optPrice;
      this.gar = gar;
      this.dilPrice = dilPrice;
      this.title = title;
   }

   @Override
   public String toString() {
        /*
        for (Locale locale : Locale.getAvailableLocales()) {
            try {
                Currency currency = Currency.getInstance(locale);
                System.out.println(locale + " / " + currency);
            } catch (Exception e) {
            }
        }
*/
      // Locale locale=Locale.getDefault();
      Locale locale = new Locale("uk_ua", "UA"); //("en", "US");

      Currency currency = Currency.getInstance(locale);
      String symbol = currency.getSymbol();

      return String.format("%8d", id) + "\t" +
              String.format("%-200s", this.title) + "\t" +
              this.sklad + "\t" +
              String.format("%10.1f", rosnichPrice) + ' ' + symbol + "\t" +
              String.format("%10.1f", optPrice) + ' ' + symbol + "\t" +
              String.format("%10.1f", dilPrice) + ' ' + symbol + "\t" +
              String.format("%10.0f", gar);

   }
}
