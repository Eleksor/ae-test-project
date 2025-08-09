package api;

import api.client.BagController;
import api.client.TokenClient;
import api.models.BagResponse;
import api.models.ItemRequest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static api.data.Constants.BASE_SKU_ID;
import static api.data.Constants.THREE_SEVERAL_ITEMS;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class GuestTests {
    static TokenClient tokenClient = new TokenClient();
    private final BagController bagController = new BagController();
    static String guestToken;

    @BeforeAll
    static void setup() {
        guestToken = tokenClient.tokenGuest();
    }

    @Test
    void testAddOneItem() {
        bagController
                .addItem(BASE_SKU_ID, 1)
                .then()
                .statusCode(202);
    }

    @Test
    void testAddThreeEqualItems() {
        bagController
                .addItem(BASE_SKU_ID, 3)
                .then()
                .statusCode(202);
    }

    @Test
    void testAddSeveralThreeItemsToCart() {
        bagController
                .addListOFItems(THREE_SEVERAL_ITEMS)
                .then()
                .statusCode(202);

        BagResponse afterAdd = bagController.getBag();
        assertThat(afterAdd.getData().getItemCount())
                .isEqualTo(3);
    }
    @Test
    void testAddOneItemThatNotExistsToCartBag() {
        bagController
                .addItem("0000324", 1)
                .then()
                .log().all()
                .statusCode(422);

        BagResponse afterAdd = bagController.getBag();

        assertThat(afterAdd.getData().getItemCount())
                .isZero();
        assertThat(afterAdd.getData().getTotal())
                .isZero();
    }

    @Test
    void addItemTest() {
        int qty = 1;

        BagResponse beforeAdd = bagController.getBag();
        assertThat(beforeAdd.getData().getItemCount())
                .isZero();

        bagController.addItem(BASE_SKU_ID, qty)
                .then()
                .log().body()
                .statusCode(202);

        BagResponse afterAdd = bagController.getBag();

        assertThat(afterAdd.getData().getItemCount())
                .isEqualTo(qty);

        assertThat(afterAdd.getData().getItems())
                .as("Bag should contain item with sku %s and quantity %d", BASE_SKU_ID, qty)
                .anySatisfy(item -> {
                    assertThat(item.getSku()).isEqualTo(BASE_SKU_ID);
                    assertThat(item.getQuantity()).isEqualTo(qty);
                });
    }

    @Test
    void testBagResponseTotal() {
        BigDecimal totalPrice = new BigDecimal("0.0");
        BigDecimal totalFromResponse;

        bagController
                .addListOFItems(THREE_SEVERAL_ITEMS)
                .then()
                .statusCode(202);

        BagResponse baggy = bagController.getBag();
        totalFromResponse = BigDecimal.valueOf(baggy.getData().getSummary().getTotal());

        List<BagResponse.Item> listItems = baggy.getData().getItems();
        for (int i = 0; i < listItems.size(); i++) {
            totalPrice = totalPrice.add(BigDecimal.valueOf(listItems.get(i).getPrice()));
        }

        assertThat(totalFromResponse.doubleValue()).isEqualTo(totalPrice.doubleValue());
    }

    @Test
    void testBagResponseItemList() {
        ItemRequest itemsFromResponse = new ItemRequest();

        bagController
                .addListOFItems(THREE_SEVERAL_ITEMS)
                .then()
                .statusCode(202);

        BagResponse baggy = bagController.getBag();
        List<BagResponse.Item> listItems = baggy.getData().getItems();

        for (int i = 0; i < listItems.size(); i++) {
            itemsFromResponse.getItems().add(new ItemRequest.Item(listItems.get(i).getSku(), listItems.get(i).getQuantity()));
        }

        assertThat(THREE_SEVERAL_ITEMS).containsExactlyInAnyOrderElementsOf(itemsFromResponse.getItems());
    }

}
