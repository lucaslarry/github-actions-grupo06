package com.vemser.rest.tests.login;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.Login;
import com.vemser.rest.model.LoginResponse;
import com.vemser.rest.model.UsuarioResponse;
import com.vemser.rest.utils.Config;
import com.vemser.rest.utils.ConstantesMensagem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class LoginTest {
    private LoginClient loginClient = new LoginClient();

    @Test
    public void testSchemaLoginValido(){
        Login login = LoginDataFactory.novoLogin();
        loginClient.realizarLogin(login)
        .then()
                .body(matchesJsonSchemaInClasspath("schemas/login.json"))
        ;
        UsuarioResponse usuario = UsuarioDataFactory.pegarUsuarioEmail(login.getEmail());
        UsuarioDataFactory.deletarUsuario(usuario);
    }

    @Test
    public void testLoginValido(){
        Login login = LoginDataFactory.novoLogin();
        LoginResponse response =
        loginClient.realizarLogin(login)
        .then()
                .statusCode(200)
                .extract().as(LoginResponse.class);
        String message = response.getMessage();
        String auth = response.getAuthorization();
        Assertions.assertAll(
                () -> Assertions.assertEquals(ConstantesMensagem.MENSAGEM_LOGIN_SUCESSO, message),
                () -> Assertions.assertNotNull(auth)
        );
        UsuarioResponse usuario = UsuarioDataFactory.pegarUsuarioEmail(login.getEmail());
        UsuarioDataFactory.deletarUsuario(usuario);
    }

    @Test
    public void testLoginComEmailDiferente(){
        Login login = LoginDataFactory.novoLogin();
        login.setEmail(Config.pegarEmailValido());

        LoginResponse response =
        loginClient.realizarLogin(login)
        .then()
                .statusCode(401)
                .extract().as(LoginResponse.class);

        String message = response.getMessage();
        String auth = response.getAuthorization();
        Assertions.assertAll(
                () -> Assertions.assertEquals(ConstantesMensagem.MENSAGEM_EMAIL_OU_SENHA_INVALIDO, message),
                () -> Assertions.assertNull(auth)
        );
        UsuarioResponse usuario = UsuarioDataFactory.pegarUsuarioPassword(login.getPassword());
        UsuarioDataFactory.deletarUsuario(usuario);

    }

    @Test
    public void testLoginComEmailVazio(){
        Login login = LoginDataFactory.loginEmailVazio();

        LoginResponse response =
        loginClient.realizarLogin(login)
        .then()
                .statusCode(400)
                .extract().as(LoginResponse.class);
        ;
        String email = response.getEmail();
        String auth = response.getAuthorization();
        Assertions.assertAll(
                () -> Assertions.assertEquals(ConstantesMensagem.MENSAGEM_EMAIL_EM_BRANCO, email),
                () -> Assertions.assertNull(auth)
        );
        UsuarioResponse usuario = UsuarioDataFactory.pegarUsuarioPassword(login.getPassword());
        UsuarioDataFactory.deletarUsuario(usuario);
    }
}
