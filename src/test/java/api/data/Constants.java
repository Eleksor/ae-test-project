package api.data;

import api.models.ItemRequest;

import java.util.ArrayList;
import java.util.List;

public class Constants {
   public static final String BASE_URI = "https://www.ae.com/";
   public static final String BASE_SKU_ID = "0043401512";
   public static final List<ItemRequest.Item> THREE_SEVERAL_ITEMS = new ArrayList<>(List.of(
           new ItemRequest.Item("0043370568", 1),
           new ItemRequest.Item("0043370154", 1),
           new ItemRequest.Item("0043518968", 1)
   ));
}
