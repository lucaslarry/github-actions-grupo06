package com.vemser.rest.client;

import com.vemser.rest.model.UsuarioRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UsuarioClient extends BaseClient{
    private static final String ID = "_id";
    private static final String USUARIOS = "/usuarios";
    private static final String USUARIOS_ID = USUARIOS+ "/{"+ID+"}";

    public Response cadastrarUsuarios(UsuarioRequest usuarioRequest) {
        return
                given()
                        .spec(super.set())
                        .body(usuarioRequest)
                        .when()
                        .post(USUARIOS);
    }


    public Response listarUsuariosComId(String idUsuario) {
        return
                given()
                        .spec(super.set())
                        .pathParam(ID, idUsuario)
                        .when()
                        .get(USUARIOS_ID)
                ;
    }

    public Response listarUsuariosQuerryParam(String path,String parametro) {
        return
                given()
                        .spec(super.set())
                        .queryParam(path, parametro)
                        .when()
                        .get(USUARIOS)
                ;
    }

    public Response deletarUsuarios(String idUsuario) {
        return
                given()
                        .spec(super.set())
                        .pathParam(ID, idUsuario)
                        .when()
                        .delete(USUARIOS_ID)
                ;
    }

    public Response atualizarUsuario(String idUsuario, UsuarioRequest usuarioRequest) {
        return
                given()
                        .spec(super.set())
                        .pathParam(ID, idUsuario)
                        .body(usuarioRequest)
                        .when()
                        .put(USUARIOS_ID)
                ;
    }

}

