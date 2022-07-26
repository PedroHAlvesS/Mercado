package br.com.compass.Sprint05.dto.response;

import br.com.compass.Sprint05.dto.request.PedidoItensDTO;
import lombok.Data;

import java.util.List;

@Data
public class ResponsePedidoDTO {
    private Long id;
    private String cpf;
    private List<PedidoItensDTO> itens;
    private Double total;
}
