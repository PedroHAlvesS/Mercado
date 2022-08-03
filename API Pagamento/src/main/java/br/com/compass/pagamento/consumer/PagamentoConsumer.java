package br.com.compass.pagamento.consumer;


import br.com.compass.pagamento.dto.banco.response.ResponseBancoPagamentoDto;
import br.com.compass.pagamento.dto.rabbitMQ.PagamentoMensagemRecebendoDto;
import br.com.compass.pagamento.service.PagamentoService;
import br.com.compass.pagamento.service.RabbitMQService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PagamentoConsumer {

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private RabbitMQService rabbitMQService;

    public static final String QUEUE = "pedidos.v1.pedidos-criados";

    @RabbitListener(queues = QUEUE)
    public void consumidor(PagamentoMensagemRecebendoDto pagamentoDto) {
        Long idSalvo = pagamentoService.salva(pagamentoDto);
        ResponseBancoPagamentoDto responseBancoPagamentoDto = pagamentoService.retornoDoBanco(idSalvo, pagamentoDto);
        rabbitMQService.enviarMensagem(responseBancoPagamentoDto, pagamentoDto);

    }

}
