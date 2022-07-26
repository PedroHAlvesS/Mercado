package br.com.compass.Sprint05.dto.item;

import lombok.Data;

@Data
public class ResponseItemDto {
    private Long id;
    private String nome;
    private String descricao;
    private String dataCriacao;
    private String dataValidade;
    private Double valor;
}
