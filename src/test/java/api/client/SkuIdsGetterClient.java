package api.client;

import api.models.ItemRequest;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static api.data.Constants.BASE_URI;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

@Getter
@Setter
public class SkuIdsGetterClient {
    RequestSpecification requestSpecification;
    TokenClient tokenClient = new TokenClient();
    private static final String SKUS_ENDPOINT = "/ugp-api/catalog/v1/category/1hnv8q2Z1266xyj/results?isFiltered=true&results=results";

    public SkuIdsGetterClient() {
        this.requestSpecification = given()
                .accept(JSON)
                .contentType(JSON)
                .baseUri(BASE_URI)
                .header("aesite", "AEO_US")
                .header("x-access-token", tokenClient.tokenGuest())
                .filter(new AllureRestAssured());
    }

    public ValidatableResponse getSkus() {
        return given(this.requestSpecification)
                //.body(request)
                .when()
                .get(SKUS_ENDPOINT)
                .then()
                .log().body();
    }
}
