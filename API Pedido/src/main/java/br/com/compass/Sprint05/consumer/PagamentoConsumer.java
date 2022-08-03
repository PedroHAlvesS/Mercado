package br.com.compass.Sprint05.consumer;

import br.com.compass.Sprint05.constants.EnumStatus;
import br.com.compass.Sprint05.constants.EnumStatusPagamento;
import br.com.compass.Sprint05.dto.rabbitMQ.PagamentoMensagemRecebendoDto;
import br.com.compass.Sprint05.exceptions.MensagemRecebidaErro;
import br.com.compass.Sprint05.exceptions.PedidoNaoEncontrado;
import br.com.compass.Sprint05.entities.PedidoEntity;
import br.com.compass.Sprint05.repository.PedidoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PagamentoConsumer {

    @Autowired
    private PedidoRepository pedidoRepository;

    public static final String QUEUE = "pagamentos.v1.pagamentos-processados";

    @RabbitListener(queues = QUEUE)
    public void consumidor(PagamentoMensagemRecebendoDto pagamentoDto) {
        PedidoEntity pedidoEntity = pedidoRepository.findById(pagamentoDto.getPedidoId()).orElseThrow(PedidoNaoEncontrado::new);

        if (pagamentoDto.getStatus().equalsIgnoreCase("APPROVED")) {
            pedidoEntity.setStatusDoPagamento(EnumStatusPagamento.APROVADO);
            pedidoEntity.setStatus(EnumStatus.FINALIZADO);
        } else if (pagamentoDto.getStatus().equalsIgnoreCase("REPROVED")) {
            pedidoEntity.setStatusDoPagamento(EnumStatusPagamento.REJEITADO);
            pedidoEntity.setStatus(EnumStatus.CANCELADO);
        } else {
            throw new MensagemRecebidaErro("Valor do campo status esta fora do padrao");
        }

        pedidoRepository.save(pedidoEntity);

    }

}
