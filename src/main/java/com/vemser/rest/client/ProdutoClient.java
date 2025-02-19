package com.vemser.rest.client;

import com.vemser.rest.model.ProdutoRequest;
import com.vemser.rest.model.ProdutoResponse;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ProdutoClient extends BaseClient{

    private final String PRODUTOS = "/produtos";
    private final String PRODUTOS_ID = "/produtos/{_id}";
    private final String ENDPOINT_ID_VAR = "_id";

    public Response cadastrarProduto(String token, ProdutoRequest produto)
    {
        return
                given()
                        .spec(super.set())
                        .auth().oauth2(token)
                        .body(produto)
                .when()
                        .post(PRODUTOS)
                ;
    }

    public Response listarProdutosCadastrados(String param, String param_data) {
        return
                given()
                        .spec(super.set())
                        .queryParam(param,param_data)
                .when()
                        .get(PRODUTOS)
                ;
    }

    public Response listarProdutosCadastradosPorID(String id) {
        return
                given()
                        .spec(super.set())
                        .pathParam(ENDPOINT_ID_VAR, id)
                .when()
                        .get(PRODUTOS_ID)
                ;
    }
}
