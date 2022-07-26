package br.com.compass.Sprint05.dto.pedido.response;

import br.com.compass.Sprint05.dto.pedido.PedidoItensDto;
import lombok.Data;

import java.util.List;

@Data
public class ResponsePedidoDTO {
    private Long id;
    private String cpf;
    private List<PedidoItensDto> itens;
    private Double total;
}
