package br.com.compass.Sprint05.service;

import br.com.compass.Sprint05.dto.pedido.response.ResponsePedidoDTO;
import br.com.compass.Sprint05.dto.rabbitMQ.PagamentoMensagemDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviaMensagem(ResponsePedidoDTO responsePedidoDTO) {
        PagamentoMensagemDto pedidoMensagemDto = new PagamentoMensagemDto(responsePedidoDTO.getId(),
                responsePedidoDTO.getTotal());
        String routingKey = "pedidos.v1.pedidos-criados";
        rabbitTemplate.convertAndSend(routingKey, pedidoMensagemDto);
    }

}
