package br.com.compass.site.dto.ApiPedido.response;

import br.com.compass.site.dto.ApiPedido.request.RequestApiPedidoCartaoDto;
import lombok.Data;

@Data
public class ResponseApiPedidoCartaoDto extends RequestApiPedidoCartaoDto {
    private Long id;
}
