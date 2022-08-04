package br.com.compass.pagamento.dto.pagamento;

import lombok.Data;

@Data
public class ResponsePagamentoDto {
    private Long id;
    private String numeroCartao;
    private String nomeCartao;
    private String codigoSeguranca;
    private String marca;
    private String mesExpiracao;
    private String anoExpiracao;
    private String moeda;
    private Double valor;
}
