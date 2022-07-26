package br.com.compass.Sprint05.dto.pedido;

import br.com.compass.Sprint05.dto.pedido.PedidoItensDto;
import lombok.Data;

@Data
public class PedidoItensDetalhadoDto extends PedidoItensDto {
    private String nome;
    private String descricao;
    private Double valor;
}
