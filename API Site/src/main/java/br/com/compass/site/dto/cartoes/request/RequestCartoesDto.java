package br.com.compass.site.dto.cartoes.request;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.NotBlank;

@Data
public class RequestCartoesDto {
    @NotBlank
    @CreditCardNumber
    private String numero;
    @NotBlank
    private String codigo;
    @NotBlank
    private String mesValidade;
    @NotBlank
    private String anoValidade;
    @NotBlank
    private String marca;
}
