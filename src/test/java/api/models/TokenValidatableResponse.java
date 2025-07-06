package api.models;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class TokenValidatableResponse {
    private TokenResponse model;
    private Response response;

    public TokenValidatableResponse(Response response) {
        this.response = response;
        model = response.as(TokenResponse.class);
    }

    @Step("Получение токена")
    public String getTokenAsString() {
        return response
                .jsonPath()
                .getString("access_token");
    }

    public TokenValidatableResponse checkStatusCode(int statusCode) {
        response
                .then()
                .statusCode(statusCode);

        return this;
    }
}
