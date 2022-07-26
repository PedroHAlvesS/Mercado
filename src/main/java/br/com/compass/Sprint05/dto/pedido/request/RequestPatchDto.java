package br.com.compass.Sprint05.dto.pedido.request;

import br.com.compass.Sprint05.dto.pedido.PedidoItensDto;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Data
public class RequestPatchDto {
    @CPF
    private String cpf;
    private List<PedidoItensDto> itens;
}
