package br.com.compass.site.dto.ApiPedido.request;

import lombok.Data;

import java.util.List;

@Data
public class RequestApiPedidoDto {
    private String cpf;
    private List<RequestApiPedidoItemDto> itens;
    private String tipoDoPagamento = "CREDIT_CARD";
    private RequestApiPedidoCartaoDto pagamento;
}
