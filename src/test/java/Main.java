import api.client.TokenClient;
import api.models.TokenValidatableResponse;

import static io.restassured.RestAssured.given;

public class Main {
    public static void main(String[] args) {
        TokenClient client = new TokenClient();
        System.out.println(client.tokenGuest());
        //LoginClient loginClient = new LoginClient();
        //loginClient.loginViaApi();

        TokenValidatableResponse tVR = new TokenValidatableResponse(
                given()
                        .baseUri("https://www.ae.com/")
                        .header("accept", "application/vnd.oracle.resource+json")
                        .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .header("authorization" , "Basic MjBlNDI2OTAtODkzYS00ODAzLTg5ZTctODliZmI0ZWJmMmZlOjVmNDk5" +
                                "NDVhLTdjMTUtNDczNi05NDgxLWU4OGVkYjQwMGNkNg==")
                        .formParam("grant_type", "password")
                        .formParam("username", "oleg.gavrikov.tm@gmail.com")
                        .formParam("password", "500496Oleg!")
                        .when()
                        .post("ugp-api/auth/oauth/v4/token")
        );




    }
}
