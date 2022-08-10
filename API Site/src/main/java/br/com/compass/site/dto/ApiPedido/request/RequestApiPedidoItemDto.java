package br.com.compass.site.dto.ApiPedido.request;

import lombok.Data;

@Data
public class RequestApiPedidoItemDto {
    private String nome;
    private String descricao;
    private String dataValidade;
    private Double valor;
    private int qtd;
}
