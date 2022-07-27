package br.com.compass.Sprint05.dto.pedido.request;


import br.com.compass.Sprint05.dto.item.request.RequestAtualizaItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestAtualizaPedidoDto {
    @CPF
    private String cpf;
    private List<RequestAtualizaItemDto> itens;
}
