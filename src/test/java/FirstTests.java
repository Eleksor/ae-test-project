import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class FirstTests {
    @Test
    void checkStatusCode() {
        ValidatableResponse vr =
                given()
                .when()
                    .get("https://www.ae.com/")
                .then()
                    .statusCode(200);
    }
}
