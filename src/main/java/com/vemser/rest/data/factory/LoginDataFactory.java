package com.vemser.rest.data.factory;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.model.Login;
import com.vemser.rest.utils.PropertiesExecutioner;
import io.restassured.response.Response;
import net.datafaker.Faker;

import java.util.Locale;

public class LoginDataFactory {
    private static final UsuarioClient usuarioClient = new UsuarioClient();
    private static final LoginClient loginClient = new LoginClient();
    static Faker faker = new Faker(new Locale("PT-BR"));

    private static Login loginData()
    {
        Login login = new Login();

        login.setEmail(PropertiesExecutioner.getEmail());
        login.setPassword(PropertiesExecutioner.getPwd());

        return login;
    }

    public static Login loginValido()
    {
        return loginData();
    }

    public static String loginTokenManager()
    {
        Login login = loginData();

        Response response =
                loginClient.logarUsuario(login)
                        .then()
                        .extract().response();

        String splitToken = response.jsonPath().get("authorization");
        String[] arrayToken = splitToken.split(" ");

        String token = arrayToken[1];

        return token;
    }
}
