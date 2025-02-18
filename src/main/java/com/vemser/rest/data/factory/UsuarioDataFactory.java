package com.vemser.rest.data.factory;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.model.*;
import com.vemser.rest.utils.GerarDados;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;


public class UsuarioDataFactory {
    private static UsuarioClient usuarioClient = new UsuarioClient();

    public static UsuarioResponse cadastrarUsuario() {
        return
                usuarioClient.cadastrarUsuarios(novoUsuario())
                        .then().extract().as(UsuarioResponse.class);

    }

    public static Response pegarUsuario(UsuarioResponse usuario) {
        return usuarioClient.listarUsuariosComId(usuario.getId());
    }


    public static void deletarUsuario(UsuarioResponse usuario) {
        usuarioClient.deletarUsuarios(usuario.getId());
    }

    public static UsuarioRequest usuarioEmailInvalido(){
        UsuarioRequest usuarioRequest = novoUsuario();
        String email = "lucas@gmail.com".split("@")[0];
        usuarioRequest.setEmail(email);
        return usuarioRequest;
    }

    public static String idInvalido(){
        return "0uxuPY0cbmQhpEz1" + "1A";
    }


    private static UsuarioRequest novoUsuario(){
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setNome(GerarDados.gerarNome());
        usuarioRequest.setEmail(GerarDados.gerarEmail());
        usuarioRequest.setPassword(GerarDados.gerarPassword());
        usuarioRequest.setAdministrador(GerarDados.gerarBoolean());
        return usuarioRequest;
    }


}
