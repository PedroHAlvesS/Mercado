package br.com.compass.site.dto.checkout.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class RequestCheckoutItensDto {
    @NotBlank
    private String skuId;
    @Positive
    @NotNull
    private int qtd;
}
