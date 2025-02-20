package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.UsuarioResponse;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.Allure;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetUsuariosTest {
    private UsuarioClient usuarioClient = new UsuarioClient();

    @Test(groups = "Contrato")
    @Tag("Contrato")
    @Story("Usuário valida schema de resposta ao listar usuários por ID")
    @Severity(SeverityLevel.NORMAL)
    @Description("Valida se o schema de resposta ao listar usuários por ID está correto")
    @Owner("Lucas Larry")
    @Step("Deve validar o schema ao listar usuários por ID")
    public void testSchemaListarUsuariosPorIDComSucesso(){
        UsuarioResponse usuario = UsuarioDataFactory.cadastrarUsuario();
        String idUsuario = usuario.getId();

        usuarioClient.listarUsuariosComId(idUsuario)
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/usuarios_por_id.json"))
        ;
        UsuarioDataFactory.deletarUsuario(usuario);
    }

    @Test(groups = "Funcional")
    @Tag("Funcional")
    @Story("Usuário consulta e valida os dados de um usuário por ID")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Valida se os dados do usuário podem ser recuperados corretamente ao consultar por ID")
    @Owner("Lucas Larry")
    @Step("Deve listar usuário por ID com sucesso")
    public void testDeveListarUsuariosPorIDComSucesso(){
        UsuarioResponse usuario = UsuarioDataFactory.cadastrarUsuario();
        String idUsuario = usuario.getId();

        UsuarioResponse response =
                usuarioClient.listarUsuariosComId(idUsuario)
                        .then()
                        .statusCode(200)
                        .extract().as(UsuarioResponse.class);
        String nome = response.getNome();
        String email = response.getEmail();
        String password = response.getPassword();
        String administrador = response.getAdministrador();


        Assert.assertNotNull(nome);
        Assert.assertNotNull(email);
        Assert.assertNotNull(password);
        Assert.assertNotNull(administrador);
        UsuarioDataFactory.deletarUsuario(usuario);
    }

    @Test(groups = "Funcional")
    @Tag("Funcional")
    @Story("Usuário tenta consultar um usuário com ID inexistente")
    @Severity(SeverityLevel.MINOR)
    @Description("Valida se ao tentar listar um usuário com ID inválido, a resposta contém a mensagem de erro correta")
    @Owner("Lucas Larry")
    @Step("Deve retornar erro ao tentar listar usuário com ID inexistente")
    public void testTentarListarUsuariosPorIDInexistente(){
        String idUsuario = UsuarioDataFactory.idInvalido();

        UsuarioResponse response =
                usuarioClient.listarUsuariosComId(idUsuario)
                        .then()
                        .statusCode(400)
                        .extract().as(UsuarioResponse.class);

        String message = response.getMessage();
        String nome = response.getNome();
        String email = response.getEmail();
        String password = response.getPassword();
        String administrador = response.getAdministrador();

        Assert.assertEquals("Usuário não encontrado", message);
        Assert.assertNull(nome);
        Assert.assertNull(email);
        Assert.assertNull(password);
        Assert.assertNull(administrador);
    }

    @Test(groups = "Contrato")
    @Tag("Contrato")
    @Story("Usuário valida schema de resposta ao listar usuários por email")
    @Severity(SeverityLevel.NORMAL)
    @Description("Valida se o schema de resposta ao listar usuários por email está correto")
    @Owner("Lucas Larry")
    @Step("Deve validar o schema ao listar usuários por email")
    public void testSchemaListarUsuariosPorEmailComSucesso(){
        UsuarioResponse usuario = UsuarioDataFactory.cadastrarUsuario();
        UsuarioResponse dadosUsuario = UsuarioDataFactory.pegarUsuario(usuario).as(UsuarioResponse.class);
        String email = dadosUsuario.getEmail();

        usuarioClient.listarUsuariosQuerryParam("email", email)
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/usuarios_por_email.json"))
        ;
        UsuarioDataFactory.deletarUsuario(usuario);
    }

    @Test(groups = "Funcional")
    @Tag("Funcional")
    @Story("Usuário consulta e valida a listagem de usuários por email")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Valida se os usuários podem ser listados corretamente ao consultar por email")
    @Owner("Lucas Larry")
    @Step("Deve listar usuários por email com sucesso")
    public void testDeveListarUsuariosPorEmailComSucesso(){
        UsuarioResponse usuario = UsuarioDataFactory.cadastrarUsuario();
        UsuarioResponse dadosUsuario = UsuarioDataFactory.pegarUsuario(usuario).as(UsuarioResponse.class);
        String email = dadosUsuario.getEmail();
        Response response =
                usuarioClient.listarUsuariosQuerryParam("email", email)
                        .then()
                        .statusCode(200)
                        .extract().response();
        List<UsuarioResponse> usuarios = response.jsonPath().getList("usuarios", UsuarioResponse.class);
        String quantidade = response.jsonPath().getString("quantidade");

        Assert.assertTrue(Integer.parseInt(quantidade) > 0);
        Assert.assertEquals(Integer.parseInt(quantidade), usuarios.size());
        UsuarioDataFactory.deletarUsuario(usuario);
    }

    @Test(groups = "Funcional")
    @Tag("Funcional")
    @Story("Usuário tenta listar usuários com email inválido")
    @Severity(SeverityLevel.MINOR)
    @Description("Valida se ao tentar listar usuários com um email inválido, a resposta contém a mensagem de erro correta")
    @Owner("Lucas Larry")
    @Step("Deve retornar erro ao tentar listar usuário com email inválido")
    public void testTentarListarUsuariosPorEmailInvalido(){
        String email = UsuarioDataFactory.usuarioEmailInvalido().getEmail();
        UsuarioResponse response =
                usuarioClient.listarUsuariosQuerryParam("email", email)
                        .then()
                        .statusCode(400)
                        .extract().as(UsuarioResponse.class);

        String emailMessage = response.getEmail();
        Assert.assertEquals("email deve ser um email válido", emailMessage);
    }
}
