package br.com.compass.Sprint05.dto.item.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestAtualizaItemDto {
    private String nome;
    private String descricao;
    private String dataValidade;
    private Double valor;
}
