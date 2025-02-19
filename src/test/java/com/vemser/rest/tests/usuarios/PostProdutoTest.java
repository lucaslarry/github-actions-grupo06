package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.ProdutoRequest;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.qameta.allure.Allure;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PostProdutoTest {
    private final ProdutoClient produtoClient = new ProdutoClient();

    private ProdutoRequest produtoRequest;
    private String id;
    private String token;

    @BeforeTest
    @Step("Configuração inicial do teste")
    public void setup()
    {
        token = LoginDataFactory.loginTokenManager();
        produtoRequest = ProdutoDataFactory.produtoValido();

        Response response =
                produtoClient.cadastrarProduto(token,produtoRequest)
                        .then()
                        .extract().response();
        id = response.jsonPath().get("_id");
    }

    @Test(groups = "Contrato")
    @Tag("Contrato")
    @Story("Usuário cadastra um produto com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Valida se o produto é cadastrado corretamente com dados válidos e schema esperado")
    @Owner("Marcelo Dalcin")
    @Step("Deve cadastrar um produto válido e validar o schema")
    public void testeSchemaDeveCadastrarProdutoValido()
    {
        token = LoginDataFactory.loginTokenManager();
        produtoRequest = ProdutoDataFactory.produtoValido();
        produtoClient.cadastrarProduto(token,produtoRequest)
                .then()
                .statusCode(20111)
                .body(matchesJsonSchemaInClasspath("schemas/produtos_cadastrar.json"));
    }

    @Test(groups = "Funcional")
    @Tag("Funcional")
    @Story("Usuário cadastra um produto com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Valida se o produto é cadastrado corretamente com dados válidos")
    @Owner("Marcelo Dalcin")
    @Step("Deve cadastrar um produto válido")
    public void testeDeveCadastrarProdutoValido()
    {
        token = LoginDataFactory.loginTokenManager();
        produtoRequest = ProdutoDataFactory.produtoValido();

        Response response = produtoClient.cadastrarProduto(token,produtoRequest)
                .then()
                .statusCode(201)
                .extract().response();


        Assert.assertNotNull(response.jsonPath().get("message"));
        Assert.assertNotNull(response.jsonPath().get("_id"));
    }
}
