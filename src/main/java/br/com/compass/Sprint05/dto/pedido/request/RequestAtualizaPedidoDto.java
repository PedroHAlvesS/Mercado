package br.com.compass.Sprint05.dto.pedido.request;


import br.com.compass.Sprint05.dto.item.request.RequestAtualizaItemDto;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Data
public class RequestAtualizaPedidoDto {
    @CPF
    private String cpf;
    private List<RequestAtualizaItemDto> itens;
}
