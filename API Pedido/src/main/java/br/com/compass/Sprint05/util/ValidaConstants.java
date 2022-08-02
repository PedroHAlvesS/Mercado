package br.com.compass.Sprint05.util;

import br.com.compass.Sprint05.constants.EnumMarcaCartao;
import br.com.compass.Sprint05.constants.EnumMoedaTipo;
import br.com.compass.Sprint05.constants.EnumTipoDoPagamento;
import br.com.compass.Sprint05.dto.pedido.request.RequestPedidoDto;
import br.com.compass.Sprint05.exceptions.MarcaCartaoInvalida;
import br.com.compass.Sprint05.exceptions.TipoDaMoedaInvalida;
import br.com.compass.Sprint05.exceptions.TipoDoPagamentoInvalido;
import org.springframework.stereotype.Component;

@Component
public class ValidaConstants {

    public void validaTipoDoPagamento(RequestPedidoDto requestDto) {
        try {
            EnumTipoDoPagamento.valueOf(requestDto.getTipoDoPagamento());
        } catch (Exception e) {
            throw new TipoDoPagamentoInvalido();
        }
    }

    public void validaMarcaCartao(RequestPedidoDto requestDto) {
        try {
            EnumMarcaCartao.valueOf(requestDto.getPagamento().getMarca());
        } catch (Exception e) {
            throw new MarcaCartaoInvalida();
        }
    }

    public void validaTipoDeMoeda(RequestPedidoDto requestDto) {
        try {
            EnumMoedaTipo.valueOf(requestDto.getPagamento().getMoeda());
        } catch (Exception e) {
            throw new TipoDaMoedaInvalida();
        }
    }

}
