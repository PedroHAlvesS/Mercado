package br.com.compass.site.dto.ApiPedido.response;

import br.com.compass.site.dto.ApiPedido.request.RequestApiPedidoItemDto;
import lombok.Data;

@Data
public class ResponseApiPedidoItemDto extends RequestApiPedidoItemDto {
    private Long id;
    private Object ofertas;
    private String dataCriacao;
}
