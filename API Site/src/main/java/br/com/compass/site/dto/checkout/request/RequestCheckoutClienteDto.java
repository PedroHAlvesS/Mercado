package br.com.compass.site.dto.checkout.request;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class RequestCheckoutClienteDto {
    @NotBlank
    @CPF
    private String clientId;
    @Positive
    private Long cartaoId;
}
