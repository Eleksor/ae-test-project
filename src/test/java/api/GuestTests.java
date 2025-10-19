package api;

import api.client.BagController;
import api.client.SkuIdsGetterClient;
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

import static api.data.Constants.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
//удаление товара, применение скидки, дискаунты(как влияют на тотал корзины), обновление qty и skuId товара(этого же
// но с другим размером и другого лота) в корзине(PATCH)
//предельное количество добавляемого товара(на 11ти приходит ошибка),
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
                .addItem(BASE_SKU_ID, 10)
                .then()
                .log().body()
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
    void testBagResponseItemListCompare() {
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

    @Test
    void testDeleteOneItem() {
        String deleteItemId;
        BagResponse afterAdd;
        BagResponse afterDelete;

         bagController
                .addItem(BASE_SKU_ID, 1)
                .then()
                .statusCode(202);

        afterAdd = bagController
                .getBag();

        deleteItemId = afterAdd
                .getData()
                .getItems()
                .get(0)
                .getItemId();

        bagController
                .deleteItem(deleteItemId)
                .then()
                .statusCode(202);

        afterDelete = bagController
                .getBag();

        assertThat(afterAdd.getData().getItems())
                .isNotEmpty();
        assertThat(afterDelete.getData().getItems())
                .isEmpty();
    }

    @Test
    void testDeleteThreeItems() {
        String deleteItemIds = "";
        BagResponse afterAdd;
        BagResponse afterDelete;

        bagController
                .addListOFItems(THREE_SEVERAL_ITEMS)
                .then()
                .statusCode(202);

        afterAdd = bagController
                .getBag();

        for (int i = 0; i < afterAdd.getData().getItems().size(); i++) {
            if(i == 0) {
                deleteItemIds = deleteItemIds + afterAdd
                        .getData()
                        .getItems()
                        .get(i)
                        .getItemId();
            } else if (i > 0 && i < afterAdd.getData().getItems().size()) {
                deleteItemIds = deleteItemIds + ", " + afterAdd
                        .getData()
                        .getItems()
                        .get(i)
                        .getItemId();;
            }
        }

        System.out.println(deleteItemIds);

        bagController
                .deleteItem(deleteItemIds)
                .then()
                .statusCode(202);

        afterDelete = bagController
                .getBag();

        assertThat(afterAdd.getData().getItems())
                .isNotEmpty();
        assertThat(afterDelete.getData().getItems())
                .isEmpty();
    }

    @Test
    void testReplaceOneItemOnSameGood() {
        String replacingItemId;
        BagResponse afterAdd;
        BagResponse afterReplace;

        bagController
                .addItem(BASE_SKU_ID, 1)
                .then()
                .statusCode(202);

        afterAdd = bagController
                .getBag();

        replacingItemId = afterAdd
                .getData()
                .getItems()
                .get(0)
                .getItemId();

        bagController
                .replaceItem( BASE_SKU_ID, 1, replacingItemId)
                .then()
                .statusCode(202);

        afterReplace = bagController
                .getBag();

        assertThat(afterAdd.getData().getItems())
                .isNotEmpty();
        assertThat(afterReplace.getData().getItems())
                .isEqualTo(afterAdd.getData().getItems());
        assertThat(afterReplace.getData().getItems().get(0).getSku())
                .isEqualTo(BASE_SKU_ID);
    }

    @Test
    void testReplaceOneItemOnDifferentGood() {
        String replacingItemId;
        BagResponse afterAdd;
        BagResponse afterReplace;

        bagController
                .addItem(BASE_SKU_ID, 1)
                .then()
                .statusCode(202);

        afterAdd = bagController
                .getBag();

        replacingItemId = afterAdd
                .getData()
                .getItems()
                .get(0)
                .getItemId();

        bagController
                .replaceItem( BASE_REPLACING_SKU_ID, 1, replacingItemId)
                .then()
                .statusCode(202);

        afterReplace = bagController
                .getBag();

        assertThat(afterAdd.getData().getItems())
                .isNotEmpty();
        assertThat(afterReplace.getData().getItems())
                .isNotEqualTo(afterAdd.getData().getItems());
        assertThat(afterReplace.getData().getItems().get(0).getSku())
                .isEqualTo(BASE_REPLACING_SKU_ID);
    }

    @Test
    void testSkus() {
        SkuIdsGetterClient skus = new SkuIdsGetterClient();
        System.out.println(skus.getSkus());
    }
}
