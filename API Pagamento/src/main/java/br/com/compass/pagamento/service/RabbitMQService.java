package br.com.compass.pagamento.service;

import br.com.compass.pagamento.dto.banco.response.ResponseBancoPagamentoDto;
import br.com.compass.pagamento.dto.rabbitMQ.PagamentoMensagemRecebendoDto;
import br.com.compass.pagamento.dto.rabbitMQ.PagamentoMensagemEnviandoDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarMensagem(ResponseBancoPagamentoDto responseBancoPagamentoDto, PagamentoMensagemRecebendoDto pagamentoMensagemDto) {
        String routingKey = "pagamentos.v1.pagamentos-criados";
        PagamentoMensagemEnviandoDto mensagemEnviandoDto = new PagamentoMensagemEnviandoDto();
        mensagemEnviandoDto.setMensagem(responseBancoPagamentoDto.getAuthorization().getReasonMessage());
        mensagemEnviandoDto.setStatus(responseBancoPagamentoDto.getStatus());
        mensagemEnviandoDto.setPedidoId(pagamentoMensagemDto.getId());

        rabbitTemplate.convertAndSend(routingKey, mensagemEnviandoDto);
    }
}
