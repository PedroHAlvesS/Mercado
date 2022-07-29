package br.com.compass.Sprint05.service;

import br.com.compass.Sprint05.dto.pedido.response.ResponsePedidoDTO;
import br.com.compass.Sprint05.dto.rabbitMQ.PagamentoMensagemDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@ExtendWith(MockitoExtension.class)
class RabbitMQServiceTest {

    @InjectMocks
    private RabbitMQService rabbitMQService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Test
    @DisplayName("Deveria enviar uma mensagem, para a fila correta")
    void enviaMensagem() {
        ResponsePedidoDTO responsePedidoDTO = new ResponsePedidoDTO();
        String routingKey = "pedidos.v1.pedidos-criados";
        PagamentoMensagemDto pagamentoMensagemDto = new PagamentoMensagemDto();

        rabbitMQService.enviaMensagem(responsePedidoDTO);

        rabbitMQService.enviaMensagem(responsePedidoDTO);

        Mockito.verify(rabbitTemplate, Mockito.atLeast(2)).convertAndSend(routingKey, pagamentoMensagemDto);

    }
}