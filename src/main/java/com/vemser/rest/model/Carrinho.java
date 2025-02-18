package com.vemser.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Carrinho {
    @JsonProperty("produtos")
    private List<ProdutoNoCarrinho> produtos = new ArrayList<>();

    public void addProd(ProdutoNoCarrinho produto) {
        produtos.add(produto);
    }
}
