import client.TestClient;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import models.TokenValidatableResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class FirstTests {

    TestClient client = new TestClient();

    @Test
    void checkStatusCode() {
        ValidatableResponse vr =
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
                        .header("authorization" , "Basic MjBlNDI2OTAtODkzYS00ODAzLTg5ZTctODliZmI0ZWJmMmZlOjVmNDk5" +
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
                        .header("authorization" , "Bearer " + client.tokenGuest())
                        .header("newrelic", "eyJ2IjpbMCwxXSwiZCI6eyJ0eSI6IkJyb3dzZXIiLCJhYyI6IjI0OTg5OTgiLCJhcCI6IjExMjAxNjg0MzkiLCJpZCI6IjY2OGIyYzlhZDUyYzBlNjIiLCJ0ciI6ImUwMjc0NTc0ZDJkYjU0YzY0NzExMmVjMTkxYWI3NDE2IiwidGkiOjE3NTA4ODcwNTI0NTQsInRrIjoiMjA0MzQ4In19")
                        .formParam("login", "oleg.gavrikov.tm@gmail.com")
                        .formParam("password", "500496Oleg!")
                        //.formParam("client", "chrome")
                        //.formParam("os", "Windows 10")
                        .when()
                        .post("ugp-api/users/v1/loginOTP");

        r.then()
                .log().body()
                .statusCode(200);
    }

}
