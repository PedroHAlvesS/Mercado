package br.com.compass.Sprint05.dto.oferta.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
