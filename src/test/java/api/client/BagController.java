package api.client;

import api.models.BagResponse;
import api.models.ItemRequest;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.List;

import static api.data.Constants.BASE_URI;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class BagController {
    RequestSpecification requestSpecification;
    TokenClient tokenClient = new TokenClient();
    private static final String ITEMS_ENDPOINT = "/ugp-api/bag/v1/items";
    private static final String BAG_ENDPOINT = "/ugp-api/bag/v1";

    public BagController() {
        this.requestSpecification = given()
                .accept(JSON)
                .contentType(JSON)
                .baseUri(BASE_URI)
                .header("aesite", "AEO_US")
                .header("x-access-token", tokenClient.tokenGuest())
                .filter(new AllureRestAssured());
    }

    @Step("Add item to bag")
    public Response addItem(String skuId, int quantity) {
        ItemRequest.Item item = new ItemRequest.Item(skuId, quantity);
        ItemRequest request = new ItemRequest(List.of(item));

        return given(this.requestSpecification)
                .body(request)
                .when()
                .post(ITEMS_ENDPOINT)
                .andReturn();
    }

    @Step("Add list of items to bag")
    public Response addListOFItems(List<ItemRequest.Item> items) {
        ItemRequest request = new ItemRequest(items);

        return given(this.requestSpecification)
                .body(request)
                .when()
                .post(ITEMS_ENDPOINT)
                .andReturn();
    }

    @Step("Get bag")
    public BagResponse getBag() {
        return given(this.requestSpecification)
                .when()
                .get(BAG_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(BagResponse.class);
    }

    @Step("Check status code")
    public ResponseSpecification checkStatusCode(int statusCode) {
        return given(this.requestSpecification)
                .then()
                .statusCode(statusCode);
    }

}
