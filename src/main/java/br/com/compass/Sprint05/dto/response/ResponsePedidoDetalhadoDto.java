package br.com.compass.Sprint05.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ResponsePedidoDetalhadoDto {
    private Long id;
    private String cpf;
    private List<ResponseItemDetalhadoDto> itens;
    private Double total;
}
