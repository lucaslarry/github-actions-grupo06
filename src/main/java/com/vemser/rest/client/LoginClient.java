package com.vemser.rest.client;

import com.vemser.rest.model.Login;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoginClient extends BaseClient{
    private final String LOGIN = "/login";

    public Response logarUsuario(Login login)
    {
        return
                given()
                        .spec(super.set())
                        .body(login)
                .when()
                        .post(LOGIN)
                ;
    }
}
