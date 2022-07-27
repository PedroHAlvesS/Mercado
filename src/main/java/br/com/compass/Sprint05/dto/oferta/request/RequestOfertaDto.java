package br.com.compass.Sprint05.dto.oferta.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class RequestOfertaDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @Positive
    private Double desconto;
    @NotBlank
    private String dataValidade;
}
