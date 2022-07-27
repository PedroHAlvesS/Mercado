package br.com.compass.Sprint05.dto.item.request;

import br.com.compass.Sprint05.dto.item.ItemOfertaDto;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

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
    private List<@Valid ItemOfertaDto> ofertas;
}
