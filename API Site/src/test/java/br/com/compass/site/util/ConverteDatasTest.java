package br.com.compass.site.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class ConverteDatasTest {
    private ConverteDatas converteDatas;

    @BeforeEach
    void inicializa() {
        this.converteDatas = new ConverteDatas();
    }

    @Test
    @DisplayName("deveria retornar a data no formato brasileiro (dd/mm/ano hh:mm:ss) e em String")
    void deveriaRetornarDataNoFormatoBrasileiro() {
        LocalDateTime dataInserida = LocalDateTime.of(2000, 10, 27, 23, 45 , 0);
        String dataEsperada = "27/10/2000 23:45:00";

        String dataDoMetodo = this.converteDatas.formataDataBrasileira(dataInserida);

        Assertions.assertEquals(dataEsperada, dataDoMetodo);
    }

}