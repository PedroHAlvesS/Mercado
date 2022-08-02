package br.com.compass.Sprint05.dto.pedido.response;

import br.com.compass.Sprint05.dto.item.response.ResponseItemDto;
import br.com.compass.Sprint05.dto.pagamento.response.ResponsePagamentoDto;
import lombok.Data;

import java.util.List;

@Data
public class ResponsePedidoDTO {
    private Long id;
    private String cpf;
    private List<ResponseItemDto> itens;
    private Double total;
    private String status;
    private String statusDoPagamento;
    private String tipoDoPagamento;
    private ResponsePagamentoDto pagamento;
}
