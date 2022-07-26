package br.com.compass.pagamento.rabbitMQ.producer;

import br.com.compass.pagamento.dto.banco.response.ResponseBancoPagamentoDto;
import br.com.compass.pagamento.dto.rabbitMQ.PagamentoMensagemEnviandoDto;
import br.com.compass.pagamento.dto.rabbitMQ.PagamentoMensagemRecebendoDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoPagamentoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarMensagem(ResponseBancoPagamentoDto responseBancoPagamentoDto, PagamentoMensagemRecebendoDto pagamentoMensagemDto) {
        String routingKey = "pagamentos.v1.pagamentos-processados";
        PagamentoMensagemEnviandoDto mensagemEnviandoDto = new PagamentoMensagemEnviandoDto();
        mensagemEnviandoDto.setMensagem(responseBancoPagamentoDto.getAuthorization().getReasonMessage());
        mensagemEnviandoDto.setStatus(responseBancoPagamentoDto.getStatus());
        mensagemEnviandoDto.setPedidoId(pagamentoMensagemDto.getId());

        rabbitTemplate.convertAndSend(routingKey, mensagemEnviandoDto);
    }
}
