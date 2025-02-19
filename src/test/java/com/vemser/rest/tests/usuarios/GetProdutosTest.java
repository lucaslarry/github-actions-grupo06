package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.ProdutoRequest;
import com.vemser.rest.model.ProdutoResponse;
import com.vemser.rest.utils.PropertiesExecutioner;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetProdutosTest {

    private final ProdutoClient produtoClient = new ProdutoClient();

    private ProdutoRequest produtoRequest;
    private ProdutoResponse produtoResponse;
    private String token;
    private String id;

    @BeforeTest
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

    @Test
    public void testAssertDeveListarProdutosCadastrados()
    {
        Response response =
                produtoClient.listarProdutosCadastrados(StringUtils.EMPTY,StringUtils.EMPTY)
                        .then()
                        .statusCode(200)
                        .extract().response();

        List<ProdutoResponse> produtos = response.jsonPath().getList("produtos", ProdutoResponse.class);

        for(var produto : produtos )
        {
            Assert.assertNotNull(produto.getNome());
            Assert.assertNotNull(produto.getDescricao());
            Assert.assertNotNull(produto.getId());
        }
    }

    @Test
    public void testSchemaDeveListarProdutoIDValido()
    {
        produtoClient.listarProdutosCadastradosPorID(PropertiesExecutioner.getId())
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/produtos_por_id.json"));
    }

    @Test
    public void testAssertDeveListarProdutoIDValido()
    {
        Response listarResponse =
                produtoClient.listarProdutosCadastradosPorID(PropertiesExecutioner.getId())
                        .then()
                        .statusCode(200)
                        .extract().response();

        Assert.assertNotNull(listarResponse.jsonPath().get("nome"));
        Assert.assertNotNull(listarResponse.jsonPath().get("descricao"));
    }

    @Test
    public void testAssertTentaListarProdutoIDInvalido()
    {
        Response response =
                produtoClient.listarProdutosCadastradosPorID("abcsdsadsadas")
                        .then()
                        .statusCode(400)
                        .extract().response();

        Assert.assertEquals("Produto não encontrado", response.jsonPath().get("message"));

    }
}
