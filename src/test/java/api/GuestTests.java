package api;

import api.client.BagController;
import api.client.TokenClient;
import api.models.BagResponse;
import api.models.ItemRequest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
    void testAddOneItemToCart() {

        ItemRequest itemRequest = new ItemRequest(List.of(new ItemRequest.Item("0042501858", 1)));

        ValidatableResponse r =
                given()
                        .baseUri("https://www.ae.com/")
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON)
                        .header("accept", "application/json")
                        .header("aesite", "AEO_US")
                        .header("content-type", "application/json")
                        .header("x-access-token", guestToken)
                        .body(itemRequest)
                        //.body(ONE_ITEM)
                        .when()
                        .post("ugp-api/bag/v1/items")
                        .then()
                        .statusCode(202);
    }

    @Test
    void testAddThreeEqualItemsToCart() {
        ItemRequest itemRequest3 = new ItemRequest(List.of(new ItemRequest.Item("0042501858", 3)));

        ValidatableResponse r =
                given()
                        .baseUri("https://www.ae.com/")
                        .header("accept", "application/json")
                        .header("aesite", "AEO_US")
                        .header("content-type", "application/json")
                        .header("x-access-token", guestToken)
                        .body(itemRequest3)
                        .when()
                        .post("ugp-api/bag/v1/items")
                        .then()
                        .statusCode(202);
    }

    @Test
    void testAddOneItemToCartBagController() {
        bagController
                .addItem(BASE_SKU_ID, 1)
                .then()
                .statusCode(202);
    }

    @Test
    void testAddThreeItemsToCartBagController() {
        bagController
                .addItem(BASE_SKU_ID, 3)
                .then()
                .statusCode(202);
    }

    @Test
    void testAddSeveralItemsToCartBagController() {
        bagController
                .addListOFItems(THREE_SEVERAL_ITEMS)
                .then()
                .statusCode(202);
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

}
