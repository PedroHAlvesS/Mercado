package br.com.compass.Sprint05.dto.rabbitMQ;

import lombok.Data;

@Data
public class PagamentoMensagemRecebendoDto {
    private String status;
    private String mensagem;
    private Long pedidoId;

}
