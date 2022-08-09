package br.com.compass.site.dto.cartoes.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RequestCartoesDto {
    @NotBlank
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
