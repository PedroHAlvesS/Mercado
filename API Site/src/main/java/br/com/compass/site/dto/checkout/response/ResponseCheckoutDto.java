package br.com.compass.site.dto.checkout.response;

import lombok.Data;

import java.util.List;

@Data
public class ResponseCheckoutDto {
    private Long numeroDoPedido;
    private Double total;
    private String status;
    private List<ResponseCheckoutItensDto> itens;
}
