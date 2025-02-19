package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.UsuarioResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetUsuariosTest {
    private UsuarioClient usuarioClient = new UsuarioClient();

    @Test
    public void testSchemaListarUsuariosPorIDComSucesso(){
        UsuarioResponse usuario = UsuarioDataFactory.cadastrarUsuario();
        String idUsuario = usuario.getId();

        usuarioClient.listarUsuariosComId(idUsuario)
        .then()
                .body(matchesJsonSchemaInClasspath("schemas/usuarios_por_id.json"))
        ;
        UsuarioDataFactory.deletarUsuario(usuario);
    }

    @Test
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

    @Test
    public void testTentarListarUsuariosPorIDInexistente(){
        String idUsuario = UsuarioDataFactory.idInvalido();

        UsuarioResponse response =
        usuarioClient.listarUsuariosComId(idUsuario)
        .then()
                .statusCode(400)
                .extract().as(UsuarioResponse.class);
        ;

        String message = response.getMessage();
        String nome = response.getNome();
        String email = response.getEmail();
        String password = response.getPassword();
        String administrador = response.getAdministrador();

        Assert.assertEquals("Usuário não encontrado",message);
        Assert.assertNull(nome);
        Assert.assertNull(email);
        Assert.assertNull(password);
        Assert.assertNull(administrador);
    }


    @Test
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

    @Test
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

        Assert.assertTrue(Integer.parseInt(quantidade)>0);
        Assert.assertEquals(Integer.parseInt(quantidade), usuarios.size());
        UsuarioDataFactory.deletarUsuario(usuario);
    }

    @Test
    public void testTentarListarUsuariosPorEmailInvalido(){
        String email = UsuarioDataFactory.usuarioEmailInvalido().getEmail();
        UsuarioResponse response =
        usuarioClient.listarUsuariosQuerryParam("email", email)
        .then()
                .statusCode(400)
                .extract().as(UsuarioResponse.class);
        ;
        String emailMessage = response.getEmail();
        Assert.assertEquals("email deve ser um email válido", emailMessage);
    }
}
