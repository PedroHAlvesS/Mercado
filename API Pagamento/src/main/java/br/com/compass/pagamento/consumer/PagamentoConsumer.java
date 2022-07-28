package br.com.compass.pagamento.consumer;


import br.com.compass.pagamento.dto.PagamentoDto;
import br.com.compass.pagamento.service.PagamentoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PagamentoConsumer {

    @Autowired
    private PagamentoService pagamentoService;

    public static final String QUEUE = "pedidos.v1.pedidos-criados";

    @RabbitListener(queues = QUEUE)
    public void consumidor(PagamentoDto pagamentoDto) {
        pagamentoService.salva(pagamentoDto);
    }

}
