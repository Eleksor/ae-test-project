package api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ItemRequest {
    private List<Item> items;

    public ItemRequest() {
        this.items = new ArrayList<>();
    }

    @Data
    @AllArgsConstructor
    public static class Item {
        private String skuId;
        private int quantity;
    }
}

