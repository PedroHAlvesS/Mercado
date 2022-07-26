package br.com.compass.Sprint05.dto.pedido.request;

import br.com.compass.Sprint05.dto.pedido.PedidoItensDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RequestPedidoDto {
    @NotBlank
    private String cpf;
    @NotNull
    @Size(min = 1)
    private List<PedidoItensDto> itens;
}
