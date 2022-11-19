package itschool;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Price-list of items with additional methods
 */
public class Items {
   private final List<Item> list;
   private LocalDate date;

   public Items(final List<Item> sublist) {
      this.list = sublist;
   }

   public Items() {
      this.list = new ArrayList<>();
      this.date = LocalDate.now();
   }

   public Items(final LocalDate date) {
      this.list = new ArrayList<>();
      this.date = Period.between(date, LocalDate.now()).isNegative() ? date : LocalDate.now();
   }

   public static Items sortCopy(final Items items, final Comparator comparator) {
      final Items temp = new Items();

      temp.list.addAll(items.list);
/*      for (int i = 0; i < items.list.size(); i++) {
         temp.list.add(items.list.get(i));
      }*/

      temp.sort(comparator);
      return temp;
   }

   public List<Item> getList() {
      return list;
   }

   public LocalDate getDate() {
      return date;
   }

   @Override
   public String toString() {
      final StringBuilder result = new StringBuilder();

      for (final Item item : list) {
         result.append(System.lineSeparator()).append(item);
      }
      return result.toString();
   }

   public void sort(final Comparator comparator) {
      this.list.sort(comparator);
   }

   public Items searchByPriceLower(final double rosnichPrice) {
      final List<Item> temp = new ArrayList<>();
      for (final Item item : this.list) {
         if (item.getRetailPrice() < rosnichPrice) {
            temp.add(item);
         }
      }
      return new Items(temp);
   }

   public Items searchByTitle(final String title) {
      final List<Item> temp = new ArrayList<>();
      for (final Item item : this.list) {
         if (item.getTitle().toLowerCase().contains(title.toLowerCase())) {
            temp.add(item);
         }
      }
      return new Items(temp);
   }
}