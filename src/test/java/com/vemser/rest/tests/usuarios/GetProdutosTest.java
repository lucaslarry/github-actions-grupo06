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
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;

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
                produtoClient.cadastrarProduto(token, produtoRequest)
                        .then()
                        .extract().response();
        id = response.jsonPath().get("_id");
    }

    @Test
    @Story("Usuário realiza um depósito com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Valida se um depósito é realizado corretamente com dados válidos")
    @Owner("Marcelo Dalcin")
    @Step("Deve listar os produtos cadastrados")
    public void testAssertDeveListarProdutosCadastrados()
    {
        Response response =
                produtoClient.listarProdutosCadastrados(StringUtils.EMPTY, StringUtils.EMPTY)
                        .then()
                        .statusCode(200)
                        .extract().response();

        List<ProdutoResponse> produtos = response.jsonPath().getList("produtos", ProdutoResponse.class);

        for (var produto : produtos) {
            Assert.assertNotNull(produto.getNome());
            Assert.assertNotNull(produto.getDescricao());
            Assert.assertNotNull(produto.getId());
        }
        Allure.addAttachment("Produtos Cadastrados", response.body().asString());
    }

    @Test
    @Story("Usuário valida o schema de produto por ID")
    @Severity(SeverityLevel.NORMAL)
    @Description("Valida se o schema de produto por ID está correto")
    @Owner("Marcelo Dalcin")
    @Step("Deve validar o schema do produto por ID")
    public void testSchemaDeveListarProdutoIDValido()
    {
        produtoClient.listarProdutosCadastradosPorID(PropertiesExecutioner.getId())
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/produtos_por_id.json"));
    }

    @Test
    @Story("Usuário consulta produto por ID válido")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Valida se um produto pode ser listado corretamente com ID válido")
    @Owner("Marcelo Dalcin")
    @Step("Deve listar o produto por ID válido")
    public void testAssertDeveListarProdutoIDValido()
    {
        Response listarResponse =
                produtoClient.listarProdutosCadastradosPorID(PropertiesExecutioner.getId())
                        .then()
                        .statusCode(200)
                        .extract().response();

        Assert.assertNotNull(listarResponse.jsonPath().get("nome"));
        Assert.assertNotNull(listarResponse.jsonPath().get("descricao"));
        Allure.addAttachment("Produto por ID", listarResponse.body().asString());
    }

    @Test
    @Story("Usuário tenta listar produto com ID inválido")
    @Severity(SeverityLevel.MINOR)
    @Description("Valida se a tentativa de listar um produto com ID inválido retorna o erro correto")
    @Owner("Marcelo Dalcin")
    @Step("Deve tentar listar produto com ID inválido")
    public void testAssertTentaListarProdutoIDInvalido()
    {
        Response response =
                produtoClient.listarProdutosCadastradosPorID("abcsdsadsadas")
                        .then()
                        .statusCode(400)
                        .extract().response();

        Assert.assertEquals("Produto não encontrado", response.jsonPath().get("message"));
        Allure.addAttachment("Erro ao tentar listar produto", response.body().asString());
    }
}
