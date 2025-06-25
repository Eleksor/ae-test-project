package client;

import models.TokenValidatableResponse;
import static io.restassured.RestAssured.given;

public class TestClient {
    public String tokenGuest() {
        TokenValidatableResponse tVR = new TokenValidatableResponse(
                given()
                        .baseUri("https://www.ae.com/")
                        .header("authorization" , "Basic MjBlNDI2OTAtODkzYS00ODAzLTg5ZTctODliZmI0ZWJmMmZlOjVmNDk5" +
                                "NDVhLTdjMTUtNDczNi05NDgxLWU4OGVkYjQwMGNkNg==")
                        .formParam("grant_type", "client_credentials")
                        .when()
                        .post("ugp-api/auth/oauth/v4/token")
        );

        return tVR.getTokenAsString();
    }
}
