package br.com.compass.site.dto.ApiPedido.request;

import lombok.Data;

@Data
public class RequestApiPedidoCartaoDto {
    private String numeroCartao;
    private String nomeCartao;
    private String codigoSeguranca;
    private String marca;
    private String mesExpiracao;
    private String anoExpiracao;
    private String moeda = "BRL";
    private Double valor;
}
