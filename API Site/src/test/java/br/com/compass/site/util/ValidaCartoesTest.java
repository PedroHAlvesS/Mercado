package br.com.compass.site.util;

import br.com.compass.site.builders.RequestCartoesDtoBuilder;
import br.com.compass.site.dto.cartoes.request.RequestCartoesDto;
import br.com.compass.site.exceptions.AnoCartaoInvalido;
import br.com.compass.site.exceptions.CodigoSegurancaInvalido;
import br.com.compass.site.exceptions.MarcaCartaoInvalida;
import br.com.compass.site.exceptions.MesCartaoInvalido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidaCartoesTest {

    private ValidaCartoes validaCartoes;

    @BeforeEach
    void inicializa() {
        this.validaCartoes = new ValidaCartoes();
    }

    @Test
    @DisplayName("Deveria passar no metodo quando os dados do cartao forem validos")
    void deveriaPassarNoMetodoQuandoOsDadosDoCartaoForValidos() {
        RequestCartoesDto cartoesDto = RequestCartoesDtoBuilder.one().now();
        validaCartoes.ValidaCartao(cartoesDto);
    }

    @Test
    @DisplayName("Deveria lancar uma exception caso o digito verificado nao seja 3 digitos")
    void deveriaLancarUmExceptionCasoODigitoVerificadorSejaDiferenteDe3Digitos() {
        RequestCartoesDto cartoesDto = RequestCartoesDtoBuilder.one().withCodigo("x").now();
        Assertions.assertThrows(CodigoSegurancaInvalido.class, () -> validaCartoes.ValidaCartao(cartoesDto));

        cartoesDto.setCodigo("yyy");
        Assertions.assertThrows(CodigoSegurancaInvalido.class, () -> validaCartoes.ValidaCartao(cartoesDto));

        cartoesDto.setCodigo("yyyy");
        Assertions.assertThrows(CodigoSegurancaInvalido.class, () -> validaCartoes.ValidaCartao(cartoesDto));

        cartoesDto.setCodigo("01X");
        Assertions.assertThrows(CodigoSegurancaInvalido.class, () -> validaCartoes.ValidaCartao(cartoesDto));

        cartoesDto.setCodigo("0000");
        Assertions.assertThrows(CodigoSegurancaInvalido.class, () -> validaCartoes.ValidaCartao(cartoesDto));

        cartoesDto.setCodigo("0");
        Assertions.assertThrows(CodigoSegurancaInvalido.class, () -> validaCartoes.ValidaCartao(cartoesDto));
    }

    @Test
    @DisplayName("Deveria lancar uma exception caso o mesValidade seja fora do 1-12")
    void deveriaLancarUmaExceptionCasoOMesValidadeSejaDiferenteDe1a12() {
        RequestCartoesDto cartoesDto = RequestCartoesDtoBuilder.one().withMesValidade("0").now();
        Assertions.assertThrows(MesCartaoInvalido.class, () -> validaCartoes.ValidaCartao(cartoesDto));

        cartoesDto.setMesValidade("-10");
        Assertions.assertThrows(MesCartaoInvalido.class, () -> validaCartoes.ValidaCartao(cartoesDto));

        cartoesDto.setMesValidade("13");
        Assertions.assertThrows(MesCartaoInvalido.class, () -> validaCartoes.ValidaCartao(cartoesDto));

        cartoesDto.setMesValidade("x10");
        Assertions.assertThrows(MesCartaoInvalido.class, () -> validaCartoes.ValidaCartao(cartoesDto));
    }

    @Test
    @DisplayName("Deveria lancar uma Exception caso o ano validade seja inferior que o ano atual")
    void deveriaLancarUmaExceptionCasoOAnoValidadeSejaInferiorQueOAnoAtual() {
        RequestCartoesDto cartoesDto = RequestCartoesDtoBuilder.one().withAnoValidade("1500").now();
        Assertions.assertThrows(AnoCartaoInvalido.class, () -> validaCartoes.ValidaCartao(cartoesDto));
    }

    @Test
    @DisplayName("Deveria lancar uma Exception caso o ano validade seja superior que o ano atual em 5 anos")
    void deveriaLancarUmaExceptionCasoOAnoValidadeSejaSuperiorQueOAnoAtualEm5Anos() {
        int anoAtual = LocalDate.now().plusYears(6L).getYear();
        String anoAtualString = String.valueOf(anoAtual);

        RequestCartoesDto cartoesDto = RequestCartoesDtoBuilder.one().withAnoValidade(anoAtualString).now();
        Assertions.assertThrows(AnoCartaoInvalido.class, () -> validaCartoes.ValidaCartao(cartoesDto));
    }

    @Test
    @DisplayName("Deveria lancar uma Exception caso o ano validade contenha letras")
    void deveriaLancarUmaExceptionCasoOAnoValidadeContenhaLetras() {
        RequestCartoesDto cartoesDto = RequestCartoesDtoBuilder.one().withAnoValidade("202x2").now();
        Assertions.assertThrows(AnoCartaoInvalido.class, () -> validaCartoes.ValidaCartao(cartoesDto));
    }

    @Test
    @DisplayName("Deveria lancar uma Exception caso a marca do cartao seja invalida")
    void deveriaLancarUmaExceptionCasoAMarcaDoCartaoSejaInvalida() {
        RequestCartoesDto cartoesDto = RequestCartoesDtoBuilder.one().withMarca("visa").now();
        Assertions.assertThrows(MarcaCartaoInvalida.class, () -> validaCartoes.ValidaCartao(cartoesDto));
    }

}