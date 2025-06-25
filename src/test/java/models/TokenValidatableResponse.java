package models;

import io.restassured.response.Response;

public class TokenValidatableResponse {
    private TokenResponse model;
    private Response response;

    public TokenValidatableResponse(Response response) {
        this.response = response;
        model = response.as(TokenResponse.class);
    }

    public String getTokenAsString() {
        return response
                .jsonPath()
                .getString("access_token");
    }
}
