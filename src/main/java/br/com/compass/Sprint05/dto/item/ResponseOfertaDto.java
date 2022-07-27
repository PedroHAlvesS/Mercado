package br.com.compass.Sprint05.dto.item;

import lombok.Data;

@Data
public class ResponseOfertaDto {
    private Long id;
    private String nome;
    private String descricao;
    private Double desconto;
    private String dataCriacao;
    private String dataValidade;
}
