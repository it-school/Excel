package itschool;

import java.util.ArrayList;
import java.util.Comparator;

public class Items {
   ArrayList<Item> list;

   public Items(ArrayList<Item> sublist1) {
      this.list = sublist1;
   }

   @Override
   public String toString() {
      String result = "";

      for (Item item1 : list) {
         result += System.lineSeparator() + item1;
      }
      return result;
   }

   public void sort(Comparator comparator) {
      this.list.sort(comparator);
   }

   public Items SearchByPriceLowerThan(double rosnichPrice) {
      ArrayList<Item> temp = new ArrayList<>();
      for (Item item : this.list) {
         if (item.rosnichPrice < rosnichPrice) {
            temp.add(item);
         }
      }
      return new Items(temp);
   }

   public Items SearchByTitle(String title) {
      ArrayList<Item> temp = new ArrayList<>();
      for (Item item : this.list) {
         if (item.title.toLowerCase().contains(title.toLowerCase())) {
            temp.add(item);
         }
      }
      return new Items(temp);
   }
}