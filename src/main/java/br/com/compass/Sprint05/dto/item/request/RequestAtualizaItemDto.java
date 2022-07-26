package br.com.compass.Sprint05.dto.item.request;

import lombok.Data;

@Data
public class RequestAtualizaItemDto {
    private String nome;
    private String descricao;
    private String dataValidade;
    private Double valor;
}
