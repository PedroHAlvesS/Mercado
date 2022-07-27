package br.com.compass.Sprint05.util;

import br.com.compass.Sprint05.exceptions.DataInvalida;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ConverteDatasTests {

    private ConverteDatas converteDatas;

    @BeforeEach
    void inicializar() {
        this.converteDatas = new ConverteDatas();
    }


    @Test
    @DisplayName("Deve retornar data no formato ISO 8601 (ano-mes-dia hora:minutos:segundos)")
    void dataDeveRetornarNoFormatoIso8601() {

        String dataInserida = "27/10/2000 10:10:10";
        LocalDateTime dataEsperada = LocalDateTime.of(2000,10,27,10,10,10);

        LocalDateTime dataDoMetodo = this.converteDatas.formataDataISO8601(dataInserida);

        Assertions.assertEquals(dataDoMetodo, dataEsperada);
    }

    @Test
    @DisplayName("Nao deveria poder inserir uma string data invalida")
    void naoDeveriaInserirDataStringInvalida() {

        String dataInvalida = "30/02/2000 10:10:10";

        Assertions.assertThrows(DataInvalida.class, () -> this.converteDatas.formataDataISO8601(dataInvalida));

    }

    @Test
    @DisplayName("Deve retornar data no formato Brasileiro (dia/mes/ano hora:minutos:segundos)")
    void dataDeveRetornarNoFormatoBrasileiro() {

        LocalDateTime dataInserida = LocalDateTime.of(2000, 10, 27, 10, 10 , 10);
        String dataEsperada = "27/10/2000 10:10:10";

        String dataDoMetodo = this.converteDatas.formataDataBrasileira(dataInserida);

        Assertions.assertEquals(dataEsperada, dataDoMetodo);
    }
}
