package itschool;

import java.util.Comparator;
import java.util.Currency;
import java.util.Locale;

/**
 * Item of the price-list being reader
 */
public class Item {
   public final static Comparator<Item> byRetailPriceAsc = Comparator.comparingDouble(o -> o.retailPrice);
   public final static Comparator<Item> byRetailPriceDesc = (o1, o2) -> Double.compare(o2.retailPrice, o1.retailPrice);

   public final static Comparator<Item> byIDAscending = Comparator.comparing(o -> o.id);
   public final static Comparator<Item> byIDDescending = (o1, o2) -> Double.compare(o2.id, o1.id);

   public final static Comparator<Item> byTitleAscending = Comparator.comparing(o -> o.title);
   //   public final static Comparator<Item> byTitleAscending = (o1, o2) -> o1.title.compareTo(o2.title);
   public static Comparator<Item> byTitleDescending = new Comparator<Item>() {
      @Override
      public int compare(final Item o1, final Item o2) {
         return o2.title.compareTo(o1.title);
      }
   };

   private int id;                  // идентификатор в БД магазина
   private String title;            // название
   private String warehouse;        // склад
   private double retailPrice;      // розничная цена
   private double wholesalePrice;   // оптовая цена
   private double dealerPrice;      // цена для дилеров
   private double guarantee;        // срок гарантии (в месяцах)

   public Item() {
      this.id = -1;
      this.guarantee = -1;
      this.dealerPrice = -1;
      this.wholesalePrice = -1;
      this.retailPrice = -1;
      this.title = "";
      this.warehouse = "";
   }

   public Item(final int id, final String title, final String warehouse, final double retailPrice, final double wholesalePrice, final double dealerPrice, final double guarantee) {
      this.id = id;
      this.title = title.strip();
      this.warehouse = warehouse.strip();
      this.retailPrice = retailPrice;
      this.wholesalePrice = wholesalePrice;
      this.dealerPrice = dealerPrice;
      this.guarantee = guarantee;
   }

   public int getId() {
      return id;
   }

   public void setId(final int id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(final String title) {
      this.title = title;
   }

   public String getWarehouse() {
      return warehouse;
   }

   public void setWarehouse(final String warehouse) {
      this.warehouse = warehouse;
   }

   public double getRetailPrice() {
      return retailPrice;
   }

   public void setRetailPrice(final double retailPrice) {
      this.retailPrice = retailPrice;
   }

   public double getWholesalePrice() {
      return wholesalePrice;
   }

   public void setWholesalePrice(final double wholesalePrice) {
      this.wholesalePrice = wholesalePrice;
   }

   public double getDealerPrice() {
      return dealerPrice;
   }

   public void setDealerPrice(final double dealerPrice) {
      this.dealerPrice = dealerPrice;
   }

   public double getGuarantee() {
      return guarantee;
   }

   public void setGuarantee(final double guarantee) {
      this.guarantee = guarantee;
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

      // final Locale locale=Locale.getDefault();
      // final Locale locale = new Locale("uk_ua", "UA");   // Locale constructor is deprecated since Java 19
//      final Locale locale = new Locale("en", "US");       // Locale constructor is deprecated since Java 19
      final Locale locale = new Locale.Builder().setLanguage("ru").setScript("Cyrl").setRegion("UA").build();
      /**
       * Language info: http://www.loc.gov/standards/iso639-2/php/code_list.php
       * Script info:   https://unicode.org/iso15924/iso15924-codes.html
       * Region info:   https://www.iso.org/obp/ui/#search
       */
      final Currency currency = Currency.getInstance(locale);
      final String symbol = currency.getSymbol();

      return String.format("%10d", id) + "\t" + String.format("%-90s", this.title) + "\t" + this.warehouse + "\t" + String.format("%20.1f", retailPrice) + ' ' + symbol + "\t" + String.format("%20.1f", wholesalePrice) + ' ' + symbol + "\t" + String.format("%20.1f", dealerPrice) + ' ' + symbol + "\t" + String.format("%10.0f мес.", guarantee);
/*
		return "<tr><td style=\"width: 16.6667%;\">"+String.format("%10d", id)+"</td>" +
				"<td style=\"width: 16.6667%;\">"+String.format("%-100s", this.title)+
				"</td><td style=\"width: 16.6667%;\">"+this.warehouse +
				"</td><td style=\"width: 16.6667%;\">"+String.format("%20.1f", retailPrice)+
				"</td><td style=\"width: 16.6667%;\">"+String.format("%20.1f", wholesalePrice) +"</td>" +
				"<td style=\"width: 16.6667%;\">"+String.format("%20.1f", dealerPrice)+"</td>" +
				"<td style=\"width: 16.6667%;\">"+String.format("%10.0f", guarantee)+"</td></tr>";
*/
   }
}