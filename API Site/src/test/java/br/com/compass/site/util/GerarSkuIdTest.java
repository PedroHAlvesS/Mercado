package br.com.compass.site.util;

import br.com.compass.site.builders.RequestItemDtoBuilder;
import br.com.compass.site.dto.item.request.RequestItemDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class GerarSkuIdTest {

    private GerarSkuId gerarSkuId;

    @BeforeEach
    void inicializa() {
        this.gerarSkuId = new GerarSkuId();
    }

    @Test
    @DisplayName("deveria gerar skuId unico")
    void deveriaGerarSkuIdUnico() {
        List<String> stringList = new ArrayList<>();
        HashSet<String> stringHashSet = new HashSet<>();
        RequestItemDto itemDto = RequestItemDtoBuilder.one().now();

        int amostraDeMeioMilhao = 500000;

        for (int i = 0; i < amostraDeMeioMilhao; i++) {
            String skuId = gerarSkuId.gerarSkuId(itemDto);
            stringList.add(skuId);
            stringHashSet.add(skuId);
        }

        int tamanhoLista = stringList.size();
        int tamanhoHastSet = stringHashSet.size();

        Assertions.assertEquals(tamanhoLista, tamanhoHastSet);
    }

    @Test
    @DisplayName("As 3 primeiras letras do SkuId deve ser as 3 do item e minusculas")
    void deveriaGerarSkuIdComAs3PrimeirasLetrasDoItemEmMinuscula() {
        RequestItemDto itemDto = RequestItemDtoBuilder.one().withNome("ABCDE").now();
        String inicioEsperado = "abc";

        String skuId = gerarSkuId.gerarSkuId(itemDto);
        String skuIdInicio = skuId.substring(0, 3);

        Assertions.assertEquals(inicioEsperado, skuIdInicio);
    }
}