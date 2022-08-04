package br.com.compass.Sprint05.dto.pagamento.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class RequestPagamentoDto {
    @NotBlank
    private String numeroCartao;
    @NotBlank
    private String nomeCartao;
    @NotBlank
    private String codigoSeguranca;
    @NotBlank
    private String marca;
    @NotBlank
    private String mesExpiracao;
    @NotBlank
    private String anoExpiracao;
    @NotBlank
    private String moeda;
    @Positive
    private Double valor;
}
