package br.com.compass.pagamento.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PagamentoDto implements Serializable {
    private Long id;
    private Double total;
}
