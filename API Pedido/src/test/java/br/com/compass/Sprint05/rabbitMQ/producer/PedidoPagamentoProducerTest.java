package br.com.compass.Sprint05.rabbitMQ.producer;

import br.com.compass.Sprint05.dto.pedido.response.ResponsePedidoDTO;
import br.com.compass.Sprint05.dto.rabbitMQ.PagamentoMensagemDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@ExtendWith(MockitoExtension.class)
class PedidoPagamentoProducerTest {

    @InjectMocks
    private PedidoPagamentoProducer pedidoPagamentoProducer;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ModelMapper modelMapper;

    @Test
    @DisplayName("Deveria enviar uma mensagem, para a fila correta")
    void enviaMensagem() {
        ResponsePedidoDTO responsePedidoDTO = new ResponsePedidoDTO();
        String routingKey = "pedidos.v1.pedidos-criados";
        PagamentoMensagemDto pagamentoMensagemDto = new PagamentoMensagemDto();

        Mockito.when(modelMapper.map(responsePedidoDTO, PagamentoMensagemDto.class)).thenReturn(pagamentoMensagemDto);

        pedidoPagamentoProducer.enviaMensagem(responsePedidoDTO);


        Mockito.verify(rabbitTemplate).convertAndSend(routingKey, pagamentoMensagemDto);

    }
}