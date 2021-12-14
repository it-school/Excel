package itschool;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Items {
   List<Item> list;
   private LocalDate date;

   public Items(List<Item> sublist1) {
      this.list = sublist1;
   }

   public Items() {
      this.list = new ArrayList<>();
      date = LocalDate.now();
   }

   public Items(LocalDate date) {
      this.list = new ArrayList<>();
      this.date = (Period.between(date, LocalDate.now()).isNegative() ? date : LocalDate.now());
   }

   public static Items sortCopy(Items items, Comparator comparator) {
      Items temp = new Items();

      temp.list.addAll(items.list);
/*      for (int i = 0; i < items.list.size(); i++) {
         temp.list.add(items.list.get(i));
      }*/

      temp.sort(comparator);
      return temp;
   }

   public LocalDate getDate() {
      return date;
   }

   @Override
   public String toString() {
      StringBuilder result = new StringBuilder();

      for (Item item : list) {
         result.append(System.lineSeparator()).append(item);
      }
      return result.toString();
   }

   public void sort(Comparator comparator) {
      this.list.sort(comparator);
   }

   public Items SearchByPriceLowerThan(double rosnichPrice) {
      List<Item> temp = new ArrayList<>();
      for (Item item : this.list) {
         if (item.rosnichPrice < rosnichPrice) {
            temp.add(item);
         }
      }
      return new Items(temp);
   }

   public Items SearchByTitle(String title) {
      List<Item> temp = new ArrayList<>();
      for (Item item : this.list) {
         if (item.title.toLowerCase().contains(title.toLowerCase())) {
            temp.add(item);
         }
      }
      return new Items(temp);
   }
}