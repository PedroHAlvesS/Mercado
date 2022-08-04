package br.com.compass.pagamento.dto.rabbitMQ;

import lombok.Data;

@Data
public class PagamentoMensagemEnviandoDto {
    private String status;
    private String mensagem;
    private Long pedidoId;
}
