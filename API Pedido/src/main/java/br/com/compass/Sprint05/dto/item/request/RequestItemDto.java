package br.com.compass.Sprint05.dto.item.request;

import br.com.compass.Sprint05.dto.oferta.request.RequestItemOfertaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private Double valor;
    private List<@Valid RequestItemOfertaDto> ofertas;
    @Positive
    @NotNull
    private int qtd;
}
