package itschool;

import java.util.Comparator;
import java.util.Currency;
import java.util.Locale;

public class Item {
   public static Comparator<Item> byPriceAsc = (o1, o2) -> Double.compare(o1.rosnichPrice, o2.rosnichPrice);
   public static Comparator<Item> byPriceDesc = (o1, o2) -> Double.compare(o2.rosnichPrice, o1.rosnichPrice);
   //   public static Comparator<Item> byTitleAscending = (o1, o2) -> o1.title.compareTo(o2.title);
   public static Comparator<Item> byTitleAscending = Comparator.comparing(o -> o.title);
   public static Comparator<Item> byTitleDescending = new Comparator<Item>() {
      @Override
      public int compare(Item o1, Item o2) {
         return o2.title.compareTo(o1.title);
      }
   };
   public static Comparator<Item> byIDAscending = Comparator.comparing(o -> o.id);
   public static Comparator<Item> byIDDescending = (o1, o2) -> Double.compare(o2.id, o1.id);

   int id;
   String title;
   String sklad;
   double rosnichPrice;
   double optPrice;
   double dilPrice;
   double gar; // in months

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
      this.title = title.strip();
      this.sklad = sklad.strip();
      this.rosnichPrice = rosnichPrice;
      this.optPrice = optPrice;
      this.dilPrice = dilPrice;
      this.gar = gar;
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

      return String.format("%10d", id) + "\t" + String.format("%-90s", this.title) + "\t" + this.sklad + "\t" + String.format("%20.1f", rosnichPrice) + ' ' + symbol + "\t" + String.format("%20.1f", optPrice) + ' ' + symbol + "\t" + String.format("%20.1f", dilPrice) + ' ' + symbol + "\t" + String.format("%10.0f мес.", gar);
/*
		return "<tr><td style=\"width: 16.6667%;\">"+String.format("%10d", id)+"</td>" +
				"<td style=\"width: 16.6667%;\">"+String.format("%-100s", this.title)+
				"</td><td style=\"width: 16.6667%;\">"+this.sklad +
				"</td><td style=\"width: 16.6667%;\">"+String.format("%20.1f", rosnichPrice)+
				"</td><td style=\"width: 16.6667%;\">"+String.format("%20.1f", optPrice) +"</td>" +
				"<td style=\"width: 16.6667%;\">"+String.format("%20.1f", dilPrice)+"</td>" +
				"<td style=\"width: 16.6667%;\">"+String.format("%10.0f", gar)+"</td></tr>";
*/
   }
}