package br.com.compass.Sprint05.dto.item.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class RequestItemDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotBlank
    private String dataValidade;
    @Positive
    private Double valor;
}
