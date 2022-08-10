package br.com.compass.site.dto.ApiPedido.response;

import lombok.Data;

import java.util.List;

@Data
public class ResponseApiPedidoDto {
    private Long id;
    private String cpf;
    private List<ResponseApiPedidoItemDto> itens;
    private Double total;
    private String status;
    private String statusDoPagamento;
    private String tipoDoPagamento;
    private ResponseApiPedidoCartaoDto pagamento;
}

