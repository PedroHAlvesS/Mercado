package br.com.compass.pagamento.rabbitMQ.consumer;


import br.com.compass.pagamento.dto.banco.response.ResponseBancoPagamentoDto;
import br.com.compass.pagamento.dto.rabbitMQ.PagamentoMensagemRecebendoDto;
import br.com.compass.pagamento.service.PagamentoService;
import br.com.compass.pagamento.rabbitMQ.producer.PedidoPagamentoProducer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoPagamentoConsumer {

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private PedidoPagamentoProducer pedidoPagamentoProducer;

    public static final String QUEUE = "pedidos.v1.pedidos-criados";

    @RabbitListener(queues = QUEUE)
    public void consumidor(PagamentoMensagemRecebendoDto pagamentoDto) {
        Long idSalvo = pagamentoService.salva(pagamentoDto);
        ResponseBancoPagamentoDto responseBancoPagamentoDto = pagamentoService.retornoDoBanco(idSalvo, pagamentoDto);
        pedidoPagamentoProducer.enviarMensagem(responseBancoPagamentoDto, pagamentoDto);

    }

}
