package br.com.compass.pagamento.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CriptografaNumeroCartaoTest {

    private CriptografaNumeroCartao criptografaNumeroCartao;

    @BeforeEach
    private void inicializa() {
        this.criptografaNumeroCartao = new CriptografaNumeroCartao();
    }

    @Test
    void deveriaCriptografarNumeroCartaoParaMD5() {
        String numeroCartao = "0";
        String md5Esperado = "cfcd208495d565ef66e7dff9f98764da";

        String numeroCartaoCriptografado = criptografaNumeroCartao.criptografaParaMD5(numeroCartao);

        Assertions.assertEquals(md5Esperado, numeroCartaoCriptografado);
    }
}