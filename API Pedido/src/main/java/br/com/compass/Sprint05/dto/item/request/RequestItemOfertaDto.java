package br.com.compass.Sprint05.dto.item.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class RequestItemOfertaDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @Positive
    @NotNull
    private Double desconto;
    @NotBlank
    private String dataValidade;
}
