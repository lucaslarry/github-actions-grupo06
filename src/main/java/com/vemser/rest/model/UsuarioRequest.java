package com.vemser.rest.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {
    private String nome;
    private String email;
    private String password;
    private String administrador;
}
