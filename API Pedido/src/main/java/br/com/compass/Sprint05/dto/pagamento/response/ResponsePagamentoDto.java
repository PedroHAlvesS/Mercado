package br.com.compass.Sprint05.dto.pagamento.response;

import lombok.Data;

@Data
public class ResponsePagamentoDto {
    private Long id;
    private String numeroCartao;
    private String nomeCartao;
    private String codigoSeguranca;
    private String marca;
    private int mesExpiracao;
    private int anoExpiracao;
    private String moeda;
    private Double valor;
}
