package br.com.compass.Sprint05.dto.item.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestItemDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotBlank
    private String dataValidade;
    @Positive
    private Double valor;
    private List<@Valid RequestItemOfertaDto> ofertas;
}
