package br.com.compass.Sprint05.dto.pagamento.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    @Max(value = 12)
    @Min(value = 1)
    private int mesExpiracao;
    @Positive
    private int anoExpiracao;
    @NotBlank
    private String moeda;
    @Positive
    private Double valor;
}
