package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.Produto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PostProdutoTest {
    private final ProdutoClient produtoClient = new ProdutoClient();

    private Produto produto;
    private String id;
    private String token;

    @BeforeTest
    public void setup()
    {
        token = LoginDataFactory.loginTokenManager();
        produto = ProdutoDataFactory.produtoValido();

        Response response =
                produtoClient.cadastrarProduto(token,produto)
                        .then()
                        .extract().response();
        id = response.jsonPath().get("_id");
    }

    @Test
    public void testeSchemaDeveCadastrarProdutoValido()
    {
        token = LoginDataFactory.loginTokenManager();
        produto = ProdutoDataFactory.produtoValido();
        produtoClient.cadastrarProduto(token,produto)
                .then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/produtos_cadastrar.json"));
    }

    @Test
    public void testeDeveCadastrarProdutoValido()
    {
        token = LoginDataFactory.loginTokenManager();
        produto = ProdutoDataFactory.produtoValido();

        Response response = produtoClient.cadastrarProduto(token,produto)
                .then()
                .statusCode(201)
                .extract().response();


        Assert.assertNotNull(response.jsonPath().get("message"));
        Assert.assertNotNull(response.jsonPath().get("_id"));
    }
}
