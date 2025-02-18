package com.vemser.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produto
{
    private String nome;
    private int preco;
    private int quantidade;
    private String descricao;
}