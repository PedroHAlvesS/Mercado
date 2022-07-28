package br.com.compass.Sprint05.dto.pedido.request;

import br.com.compass.Sprint05.dto.item.request.RequestItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestPedidoDto {
    @CPF(message = "Cpf inválido")
    private String cpf;
    @NotNull
    @Size(min = 1)
    private List<@Valid RequestItemDto> itens;
}
