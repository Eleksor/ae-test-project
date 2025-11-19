package api.data;

import api.models.ItemRequest;

import java.util.ArrayList;
import java.util.List;

public class Constants {
   public static final String BASE_URI = "https://www.ae.com/";
   public static String BASE_SKU_ID = "0043960970";
   public static String BASE_REPLACING_SKU_ID = "0043684018";
   public static List<ItemRequest.Item> THREE_SEVERAL_ITEMS = new ArrayList<>(List.of(
           new ItemRequest.Item("0043782069", 1),
           new ItemRequest.Item("0043771427", 1),
           new ItemRequest.Item("0043901156", 1)
   ));

   public Constants(List<String> strings) {
      Constants.BASE_SKU_ID = strings.get(0);
      Constants.BASE_REPLACING_SKU_ID = strings.get(1);
      Constants.THREE_SEVERAL_ITEMS = new ArrayList<>(List.of(
              new ItemRequest.Item(strings.get(strings.size()-1), 1),
              new ItemRequest.Item(strings.get(strings.size()-2), 1),
              new ItemRequest.Item(strings.get(strings.size()-3), 1)
      ));
   }
}
