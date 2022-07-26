package br.com.compass.Sprint05.dto.response;

import br.com.compass.Sprint05.dto.request.PedidoItensDTO;
import lombok.Data;

@Data
public class ResponseItemDetalhadoDto extends PedidoItensDTO {
    private String nome;
    private String descricao;
    private Double valor;
}
