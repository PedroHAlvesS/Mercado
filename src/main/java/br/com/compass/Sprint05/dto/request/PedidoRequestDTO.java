package br.com.compass.Sprint05.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PedidoRequestDTO {
    @NotBlank
    private String cpf;
    @NotNull
    @Size(min = 1)
    private List<PedidoItensDTO> itens;
}
