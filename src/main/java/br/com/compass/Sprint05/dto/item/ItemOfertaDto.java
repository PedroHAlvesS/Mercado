package br.com.compass.Sprint05.dto.item;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class ItemOfertaDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @Positive
    private Double desconto;
    @NotBlank
    private String dataValidade;
}
