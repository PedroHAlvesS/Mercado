package br.com.compass.Sprint05.util;

import br.com.compass.Sprint05.dto.pagamento.request.RequestPagamentoDto;
import br.com.compass.Sprint05.dto.pedido.request.RequestPedidoDto;
import br.com.compass.Sprint05.exceptions.MarcaCartaoInvalida;
import br.com.compass.Sprint05.exceptions.TipoDaMoedaInvalida;
import br.com.compass.Sprint05.exceptions.TipoDoPagamentoInvalido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValidaConstantsTest {

    private ValidaConstants validaConstants;

    private RequestPedidoDto requestPedidoDto;

    @Test
    @DisplayName("Deveria passar com tipo de pagamento valido")
    void deveriaPassarTipoDePagamentoValido() {
        buildTiposValidos();

        this.validaConstants.validaTipoDoPagamento(this.requestPedidoDto);

    }

    @Test
    @DisplayName("Deveria lancar uma exception de pagamento invalido quando recebe um tipo invalido")
    void deveriaLancarUmaExceptionComTipoInvalidoDePagamento() {
        buildTiposInvalidos();

        Assertions.assertThrows(TipoDoPagamentoInvalido.class, () -> this.validaConstants.validaTipoDoPagamento(this.requestPedidoDto));

    }

    @Test
    @DisplayName("Deveria passar marca com tipo valido")
    void deveriaPassarMarcaComTipoValido() {
        buildTiposValidos();

        this.validaConstants.validaMarcaCartao(this.requestPedidoDto);

    }

    @Test
    @DisplayName("Deveria lancar uma exception quando a marca do cartao for invalida")
    void deveriaLancarUmaExceptionComMarcaDoCartaoInvalida() {
        buildTiposInvalidos();

        Assertions.assertThrows(MarcaCartaoInvalida.class, () -> this.validaConstants.validaMarcaCartao(this.requestPedidoDto));

    }

    @Test
    @DisplayName("Deveria passar moeda com tipo valido")
    void deveriaPassarMoedaComTipoValido() {
        buildTiposValidos();

        this.validaConstants.validaTipoDeMoeda(this.requestPedidoDto);

    }

    @Test
    @DisplayName("Deveria lancar uma exception quando o tipo de moeda for invalido")
    void deveriaLancarUmaExceptionComTipoDeMoedaInvalida() {
        buildTiposInvalidos();

        Assertions.assertThrows(TipoDaMoedaInvalida.class, () -> this.validaConstants.validaTipoDeMoeda(this.requestPedidoDto));

    }

    private void buildTiposValidos() {
        this.requestPedidoDto = new RequestPedidoDto();
        this.requestPedidoDto.setTipoDoPagamento("PIX");

        RequestPagamentoDto pagamentoDto = new RequestPagamentoDto();
        pagamentoDto.setMarca("VISA");
        pagamentoDto.setMoeda("BRL");

        this.requestPedidoDto.setPagamento(pagamentoDto);

        this.validaConstants = new ValidaConstants();

    }


    private void buildTiposInvalidos() {
        this.requestPedidoDto = new RequestPedidoDto();
        this.requestPedidoDto.setTipoDoPagamento("xxTestexx");

        RequestPagamentoDto pagamentoDto = new RequestPagamentoDto();
        pagamentoDto.setMarca("xxTestexx");
        pagamentoDto.setMoeda("xxTestexx");

        this.requestPedidoDto.setPagamento(pagamentoDto);

        this.validaConstants = new ValidaConstants();
    }
}