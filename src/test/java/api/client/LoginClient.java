package api.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;

import static io.restassured.RestAssured.given;
@Getter
public class LoginClient {
    TokenClient tokenClient = new TokenClient();

    private String bearerToken = tokenClient.tokenBearer();
    private String passwordToken;

    public void loginViaApi() {
        Response login =
                given()
                        .baseUri("https://www.ae.com/")
                        .contentType(ContentType.URLENC)
                        .header("aelang", "en_US")
                        .header("aesite", "AEO_US")
                        .header("aecountry", "US")
                        .header("authorization" , "Bearer " + this.getBearerToken())
                        .body("login=oleg.gavrikov.tm%40gmail.com&password=500496Oleg%21&client=chrome&os=Windows+10")
                        .when()
                        .post("ugp-api/users/v1/loginOTP");

        login.then()
                .log().body()
                .statusCode(200);
        passwordToken = tokenClient.tokenPassword(this.getBearerToken());
    }
}
