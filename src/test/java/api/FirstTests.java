package api;

import api.client.LoginClient;
import api.client.TokenClient;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import api.models.TokenValidatableResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class FirstTests {

    TokenClient client = new TokenClient();
    String guestToken = client.tokenGuest();

    @Test
    void checkStatusCode() {
        //ValidatableResponse vr =
                given()
                        .when()
                        .get("https://www.ae.com/")
                        .then()
                        .statusCode(200);
    }

    @Test
    void guestToken() {
        Response r =
                given()
                        .baseUri("https://www.ae.com/")
                        //.header("aelang", "en_US")
                        //.header("aesite", "AEO_US")
                        //.header("aecountry", "US")
                        .header("authorization", "Basic MjBlNDI2OTAtODkzYS00ODAzLTg5ZTctODliZmI0ZWJmMmZlOjVmNDk5" +
                                "NDVhLTdjMTUtNDczNi05NDgxLWU4OGVkYjQwMGNkNg==")
                        .formParam("grant_type", "client_credentials")
                        .when()
                        .post("ugp-api/auth/oauth/v4/token");

        TokenValidatableResponse tVR = new TokenValidatableResponse(r);
        System.out.println(tVR.getTokenAsString());

        r.then()
                .log().body()
                .statusCode(200);
    }

    @Test
    void loginApi() {
        Response r =
                given()
                        .baseUri("https://www.ae.com/")
                        .contentType(ContentType.URLENC)
                        .header("aelang", "en_US")
                        .header("aesite", "AEO_US")
                        .header("aecountry", "US")
                        .header("authorization", "Bearer " + client.tokenBearer())
                        .body("login=oleg.gavrikov.tm%40gmail.com&password=500496Oleg%21&client=chrome&os=Windows+10")
                        .when()
                        .post("ugp-api/users/v1/loginOTP");

        r.then()
                .log().body()
                .statusCode(200);
    }

    @Test
    void testBagAdd() {

        LoginClient loginClient = new LoginClient();
        loginClient.loginViaApi();

        String body = """
                {"items":[{"skuId":"0042501858","quantity":1}]}
                """;

        Response r =
                given()
                        .baseUri("https://www.ae.com/")
                        .contentType("application/json; charset = utf-8")
                        .header("accept", "application/json")
                        .header("accept-encoding", "gzip, deflate, br, zstd")
                        .header("aesite", "AEO_US")
                        .header("aecountry", "US")
                        .header("authorization", "Bearer " + loginClient.getBearerToken())
                        .header("x-access-token", loginClient.getBearerToken())
                        .header("newrelic", "eyJ2IjpbMCwxXSwiZCI6eyJ0eSI6IkJyb3dzZXIiLCJhYyI6IjI0OTg5OTgiLCJhcCI6IjExMjAxNjg0MzkiLCJpZCI6ImIyZTQ2YTk3N2Y4NWM4ODEiLCJ0ciI6IjVkMWU5MjkwOThiNTkxNzI0MmY2ODhlMjYzOTk3NmZjIiwidGkiOjE3NTEwNTk3NDM3MjksInRrIjoiMjA0MzQ4In19")
                        .urlEncodingEnabled(true)
                        .body(body)
                        .when()
                        .post("ugp-api/bag/v1/items");
        r.then()
                .log().body()
                .statusCode(202);

    }

    @Test
    void getBag() {
        LoginClient loginClient = new LoginClient();
        loginClient.loginViaApi();

        Response r =
                given()
                        .baseUri("https://www.ae.com/")
                        .accept("application/json")
                        .header("aesite", "AEO_US")
                        //.header("aelang", "en_US")
                        //.header("aecountry", "US")
                        .header("authorization", "Bearer " + loginClient.getBearerToken())
                        .header("x-access-token", loginClient.getBearerToken())
                        //.header("newrelic", "eyJ2IjpbMCwxXSwiZCI6eyJ0eSI6IkJyb3dzZXIiLCJhYyI6IjI0OTg5OTgiLCJhcCI6IjExMjAxNjg0MzkiLCJpZCI6ImIyZTQ2YTk3N2Y4NWM4ODEiLCJ0ciI6IjVkMWU5MjkwOThiNTkxNzI0MmY2ODhlMjYzOTk3NmZjIiwidGkiOjE3NTEwNTk3NDM3MjksInRrIjoiMjA0MzQ4In19")
                        .param("couponErrorBehavior", "cart")
                        .param("inventoryCheck", "true")
                        .when()
                        .get("ugp-api/bag/v1");

        r.then()
                .log().body()
                .statusCode(200);
    }

    @Test
    void getBagCount() {
        LoginClient loginClient = new LoginClient();
        loginClient.loginViaApi();

        Response r =
                given()
                        .baseUri("https://www.ae.com/")
                        .accept("application/json")
                        //.contentType(ContentType.TEXT)
                        .header("aesite", "AEO_US")
                        .header("aelang", "en_US")
                        .header("aecountry", "US")
                        .header("x-access-token", loginClient.getPasswordToken())
                        .header("newrelic", "eyJ2IjpbMCwxXSwiZCI6eyJ0eSI6IkJyb3dzZXIiLCJhYyI6IjI0OTg5OTgiLCJhcCI6IjExMjAxNjg0MzkiLCJpZCI6ImIyZTQ2YTk3N2Y4NWM4ODEiLCJ0ciI6IjVkMWU5MjkwOThiNTkxNzI0MmY2ODhlMjYzOTk3NmZjIiwidGkiOjE3NTEwNTk3NDM3MjksInRrIjoiMjA0MzQ4In19")
                        .header("authorization", "Bearer " + loginClient.getPasswordToken())
                        //.param("inventoryCheck", "true")
                        .when()
                        .get("ugp-api/bag/v1/count");

        r.then()
                .log().body()
                .statusCode(200);
    }

    @Test
    void getBag1Count() {
        LoginClient loginClient = new LoginClient();
        loginClient.loginViaApi();

        Response r =
                given()
                        .baseUri("https://www.ae.com/")
                        .accept("application/json")
                        //.contentType(ContentType.TEXT)
                        .header("aesite", "AEO_US")
                        .header("aelang", "en_US")
                        .header("aecountry", "US")
                        .header("x-access-token", loginClient.getPasswordToken())
                        .header("newrelic", "eyJ2IjpbMCwxXSwiZCI6eyJ0eSI6IkJyb3dzZXIiLCJhYyI6IjI0OTg5OTgiLCJhcCI6IjExMjAxNjg0MzkiLCJpZCI6ImIyZTQ2YTk3N2Y4NWM4ODEiLCJ0ciI6IjVkMWU5MjkwOThiNTkxNzI0MmY2ODhlMjYzOTk3NmZjIiwidGkiOjE3NTEwNTk3NDM3MjksInRrIjoiMjA0MzQ4In19")
                        .header("authorization", "Bearer " + loginClient.getPasswordToken())
                        //.param("inventoryCheck", "true")
                        .when()
                        .get("ugp-api/bag/v1/count");

        r.then()
                .log().body()
                .statusCode(200);
    }

}
