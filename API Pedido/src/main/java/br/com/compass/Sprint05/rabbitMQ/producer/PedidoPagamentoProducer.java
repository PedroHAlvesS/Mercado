package br.com.compass.Sprint05.rabbitMQ.producer;

import br.com.compass.Sprint05.dto.pedido.response.ResponsePedidoDTO;
import br.com.compass.Sprint05.dto.rabbitMQ.PagamentoMensagemDto;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoPagamentoProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ModelMapper modelMapper;

    public void enviaMensagem(ResponsePedidoDTO responsePedidoDTO) {
        PagamentoMensagemDto mensagemDto = modelMapper.map(responsePedidoDTO, PagamentoMensagemDto.class);
        String routingKey = "pedidos.v1.pedidos-criados";
        rabbitTemplate.convertAndSend(routingKey, mensagemDto);
    }



}
