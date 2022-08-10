package br.com.compass.site.dto.checkout.response;

import lombok.Data;

@Data
public class ResponseCheckoutItensDto {
    private String nome;
    private Double valor;
}
