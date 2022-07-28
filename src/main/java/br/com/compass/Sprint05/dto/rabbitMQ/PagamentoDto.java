package br.com.compass.Sprint05.dto.rabbitMQ;

import lombok.Data;

import java.io.Serializable;

@Data
public class PagamentoDto implements Serializable {
    private Long id;
    private Double total;
}
