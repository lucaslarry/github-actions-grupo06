package com.vemser.rest.data.factory;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.model.ProdutoRequest;
import com.vemser.rest.model.ProdutoResponse;
import net.datafaker.Faker;
import java.util.Locale;
import java.util.Random;

public class ProdutoDataFactory {
    static Faker faker = new Faker(new Locale("PT-BR"));
    private static final ProdutoClient produtoClient = new ProdutoClient();
    private static final LoginClient loginClient = new LoginClient();

    private static ProdutoRequest novoProduto()
    {
        ProdutoRequest produto = new ProdutoRequest();

        Random geradorBoolean = new Random();

        produto.setNome(faker.commerce().productName());
        produto.setPreco(faker.number().numberBetween(0,10000));
        produto.setDescricao(faker.commerce().department());
        produto.setQuantidade(faker.number().numberBetween(1,1000));

        return produto;
    }

    public static ProdutoRequest produtoValido()
    {
        return novoProduto();
    }
}
