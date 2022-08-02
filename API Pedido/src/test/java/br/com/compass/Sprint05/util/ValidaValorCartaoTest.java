package br.com.compass.Sprint05.util;

import br.com.compass.Sprint05.exceptions.ValorCartaoMaior;
import br.com.compass.Sprint05.exceptions.ValorCartaoMenor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidaValorCartaoTest {

    private ValidaValorCartao validaValorCartao;

    @BeforeEach
    void inicializa() {
        this.validaValorCartao = new ValidaValorCartao();
    }

    @Test
    @DisplayName("Deve passar quando o Valor do Cartao e Valor Total forem iguais")
    void deveriaPassarQuandoValorCartaoForIgualValorTotal() {
        Double valorTotal = 5.0;
        Double valorCartao = 5.0;

        this.validaValorCartao.validaValorDoCartao(valorTotal, valorCartao);

    }

    @Test
    @DisplayName("Deve lanca Exception Quando Valor Cartao for maior que o Valor Total")
    void deveriaLancarExceptionQuandoValorCartaoForMaiorQueValorTotal() {
        Double valorTotal = 5.0;
        Double valorCartao = 7.0;

        Assertions.assertThrows(ValorCartaoMaior.class, () -> this.validaValorCartao.validaValorDoCartao(valorTotal, valorCartao));

    }

    @Test
    @DisplayName("Deve lanca Exception Quando Valor Cartao for menor que o Valor Total")
    void deveriaLancarExceptionQuandoValorCartaoForMenorQueValorTotal() {
        Double valorTotal = 5.0;
        Double valorCartao = 3.0;

        Assertions.assertThrows(ValorCartaoMenor.class, () -> this.validaValorCartao.validaValorDoCartao(valorTotal, valorCartao));

    }

}