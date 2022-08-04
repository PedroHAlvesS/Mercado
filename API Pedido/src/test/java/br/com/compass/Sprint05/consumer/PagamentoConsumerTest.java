package br.com.compass.Sprint05.consumer;

import br.com.compass.Sprint05.constants.EnumStatus;
import br.com.compass.Sprint05.constants.EnumStatusPagamento;
import br.com.compass.Sprint05.dto.rabbitMQ.PagamentoMensagemRecebendoDto;
import br.com.compass.Sprint05.entities.PedidoEntity;
import br.com.compass.Sprint05.exceptions.MensagemRecebidaErro;
import br.com.compass.Sprint05.exceptions.PedidoNaoEncontrado;
import br.com.compass.Sprint05.repository.PedidoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PagamentoConsumerTest {

    @InjectMocks
    private PagamentoConsumer pagamentoConsumer;

    @Mock
    private PedidoRepository pedidoRepository;

    @Test
    @DisplayName("Deveria salvar um pedido com mensagem de status aprovado")
    void deveriaSalvarUmPedidoComMensagemDeStatusAprovado() {
        PagamentoMensagemRecebendoDto pagamentoMensagemRecebendoDto = new PagamentoMensagemRecebendoDto();
        pagamentoMensagemRecebendoDto.setMensagem("teste");
        pagamentoMensagemRecebendoDto.setStatus("APPROVED");
        pagamentoMensagemRecebendoDto.setPedidoId(1L);

        PedidoEntity pedidoEntity = new PedidoEntity();

        Mockito.when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoEntity));

        pagamentoConsumer.consumidor(pagamentoMensagemRecebendoDto);

        Mockito.verify(pedidoRepository).save(pedidoEntity);
        Assertions.assertEquals(pedidoEntity.getStatus(), EnumStatus.FINALIZADO);
        Assertions.assertEquals(pedidoEntity.getStatusDoPagamento(), EnumStatusPagamento.APROVADO);
    }

    @Test
    @DisplayName("Deveria salvar um pedido com mensagem de status reprovado")
    void deveriaSalvarUmPedidoComStatusReprovado() {
        PagamentoMensagemRecebendoDto pagamentoMensagemRecebendoDto = new PagamentoMensagemRecebendoDto();
        pagamentoMensagemRecebendoDto.setMensagem("teste");
        pagamentoMensagemRecebendoDto.setStatus("REPROVED");
        pagamentoMensagemRecebendoDto.setPedidoId(1L);

        PedidoEntity pedidoEntity = new PedidoEntity();

        Mockito.when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoEntity));

        pagamentoConsumer.consumidor(pagamentoMensagemRecebendoDto);

        Mockito.verify(pedidoRepository).save(pedidoEntity);
        Assertions.assertEquals(pedidoEntity.getStatus(), EnumStatus.CANCELADO);
        Assertions.assertEquals(pedidoEntity.getStatusDoPagamento(), EnumStatusPagamento.REJEITADO);
    }

    @Test
    @DisplayName("Deveria lancar uma exception em pedido nao existente")
    void deveriaLancarUmExceptionNoPedidoInexistente() {
        PagamentoMensagemRecebendoDto pagamentoMensagemRecebendoDto = new PagamentoMensagemRecebendoDto();
        pagamentoMensagemRecebendoDto.setPedidoId(20L);

        Assertions.assertThrows(PedidoNaoEncontrado.class, () -> pagamentoConsumer.consumidor(pagamentoMensagemRecebendoDto));

    }

    @Test
    @DisplayName("Deveria lancar uma exception com status anormal")
    void deveriaLancarUmExceptionComStatusAnormal() {
        PagamentoMensagemRecebendoDto pagamentoMensagemRecebendoDto = new PagamentoMensagemRecebendoDto();
        pagamentoMensagemRecebendoDto.setPedidoId(1L);
        pagamentoMensagemRecebendoDto.setStatus("teste");

        PedidoEntity pedidoEntity = new PedidoEntity();
        Mockito.when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoEntity));

        Assertions.assertThrows(MensagemRecebidaErro.class, () -> pagamentoConsumer.consumidor(pagamentoMensagemRecebendoDto));

    }


}