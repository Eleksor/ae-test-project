package api.client;

import api.models.TokenValidatableResponse;
import static io.restassured.RestAssured.given;

public class TokenClient {
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

    public String tokenBearer() {
        TokenValidatableResponse tVR = new TokenValidatableResponse(
                given()
                        .baseUri("https://www.ae.com/")
                        .header("authorization" , "Basic MjBlNDI2OTAtODkzYS00ODAzLTg5ZTctODliZmI0ZWJmMmZlOjVmNDk5" +
                                "NDVhLTdjMTUtNDczNi05NDgxLWU4OGVkYjQwMGNkNg==")
                        .formParam("grant_type", "client_credentials")
                        .when()
                        .post("ugp-api/auth/oauth/v5/token")
        );

        return tVR.getTokenAsString();

    }

    public String tokenPassword(String bearerToken) {
        TokenValidatableResponse tVR = new TokenValidatableResponse(
        given()
                .baseUri("https://www.ae.com/")
                //.contentType(ContentType.URLENC)
                .header("accept", "application/vnd.oracle.resource+json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("authorization" , "Basic MjBlNDI2OTAtODkzYS00ODAzLTg5ZTctODliZmI0ZWJmMmZlOjVmNDk5" +
                        "NDVhLTdjMTUtNDczNi05NDgxLWU4OGVkYjQwMGNkNg==")
                .header("aelang", "en_US")
                .header("aesite", "AEO_US")
                .header("x-access-token", bearerToken)
                .formParam("grant_type", "password")
                .formParam("username", "oleg.gavrikov.tm@gmail.com")
                .formParam("password", "500496Oleg!")
                //.body("grant_type=password&username=oleg.gavrikov.tm%40gmail.com&password=500496Oleg%21")
                .when()
                .post("ugp-api/auth/oauth/v4/token")

        );

        return tVR.getTokenAsString();
    }

}
