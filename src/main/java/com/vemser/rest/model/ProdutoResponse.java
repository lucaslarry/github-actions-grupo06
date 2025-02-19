package com.vemser.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponse
{
    private String nome;
    private int preco;
    private int quantidade;
    private String descricao;
    @JsonProperty("_id")
    private String id;
    private String imagem;
}