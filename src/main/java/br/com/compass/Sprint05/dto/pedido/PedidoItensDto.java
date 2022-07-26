package br.com.compass.Sprint05.dto.pedido;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PedidoItensDto {
    @NotNull
    private Long itemId;
}
