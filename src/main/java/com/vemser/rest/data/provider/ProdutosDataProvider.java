package com.vemser.rest.data.provider;

import com.vemser.rest.data.factory.ProdutoDataFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class ProdutosDataProvider {

    public static Stream<Arguments> fornecerProdutosInvalidos() {
        return Stream.of(
                Arguments.of(ProdutoDataFactory.produtoValido(), "Rota exclusiva para administradores",ProdutoDataFactory.pegarSemAuth(),403),
                Arguments.of(ProdutoDataFactory.produtoValido(), "Token de acesso ausente, inválido, expirado ou usuário do token não existe mais","",401)

        );
    }
}
