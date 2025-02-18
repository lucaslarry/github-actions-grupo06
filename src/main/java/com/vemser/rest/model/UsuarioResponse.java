package com.vemser.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

    private String nome;
    private String email;
    private String password;
    private String administrador;
    private String message;
    private String idCarrinho;
    private String quantidade;
    @JsonProperty("_id")
    private String id;
}
