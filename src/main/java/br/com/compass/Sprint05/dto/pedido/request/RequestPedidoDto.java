package br.com.compass.Sprint05.dto.pedido.request;

import br.com.compass.Sprint05.dto.item.request.RequestItemDto;
import br.com.compass.Sprint05.dto.pedido.PedidoItensDto;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RequestPedidoDto {
    @CPF(message = "Cpf inválido")
    private String cpf;
    @NotNull
    @Size(min = 1)
    private List<@Valid RequestItemDto> itens;
}
