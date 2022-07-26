package br.com.compass.Sprint05.dto.pedido.response;

import br.com.compass.Sprint05.dto.pedido.PedidoItensDetalhadoDto;
import lombok.Data;

import java.util.List;

@Data
public class ResponsePedidoDetalhadoDto {
    private Long id;
    private String cpf;
    private List<PedidoItensDetalhadoDto> itens;
    private Double total;
}
