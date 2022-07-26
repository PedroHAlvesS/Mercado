package br.com.compass.Sprint05.dto.pedido.request;

import br.com.compass.Sprint05.dto.pedido.PedidoItensDto;
import lombok.Data;

import java.util.List;

@Data
public class RequestPatchDto {
    private String cpf;
    private List<PedidoItensDto> itens;
}
