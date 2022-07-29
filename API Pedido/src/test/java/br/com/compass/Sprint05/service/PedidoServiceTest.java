package br.com.compass.Sprint05.service;

import br.com.compass.Sprint05.dto.item.request.RequestAtualizaItemDto;
import br.com.compass.Sprint05.dto.item.request.RequestItemDto;
import br.com.compass.Sprint05.dto.pedido.request.RequestAtualizaPedidoDto;
import br.com.compass.Sprint05.dto.pedido.request.RequestPedidoDto;
import br.com.compass.Sprint05.dto.pedido.response.ResponsePedidoDTO;
import br.com.compass.Sprint05.models.PedidoEntity;
import br.com.compass.Sprint05.repository.PedidoRepository;
import br.com.compass.Sprint05.util.ValidaDatas;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {
    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ValidaDatas validaDatas;


    @Test
    @DisplayName("Deve salvar um item, além de o total ser a soma do valor de cada item")
    void deveriaSalvarUmPedidoCorretamente() {
        RequestItemDto itemDto = RequestItemDto.builder()
                .nome("Carro")
                .descricao("Automóvel")
                .valor(879.58)
                .dataValidade("27/12/2011 15:10:15")
                .build();

        List<RequestItemDto> itensDtoList = new ArrayList<>();
        itensDtoList.add(itemDto);

        RequestPedidoDto pedidoDto = RequestPedidoDto.builder()
                .cpf("xxx-xxx-xxx-yy")
                .itens(itensDtoList)
                .build();

        PedidoEntity pedidoEntity = PedidoEntity.builder()
                .cpf(pedidoDto.getCpf())
                .id(1L)
                .build();


        Mockito.when(modelMapper.map(pedidoDto, PedidoEntity.class)).thenReturn(pedidoEntity);
        Mockito.when(pedidoRepository.save(pedidoEntity)).thenReturn(pedidoEntity);

        pedidoService.salva(pedidoDto);
        Assertions.assertEquals(itemDto.getValor(), pedidoEntity.getTotal());
        Mockito.verify(modelMapper).map(pedidoEntity, ResponsePedidoDTO.class);

    }

    @Test
    @DisplayName("Deveria deletar com sucesso um pedido já criado")
    void deveriaDeletarComSucesso() {
        PedidoEntity pedidoEntity = new PedidoEntity();
        Mockito.when(pedidoRepository.findById(1l)).thenReturn(Optional.of(pedidoEntity));

        pedidoService.deleta(1l);
        Mockito.verify(pedidoRepository).delete(pedidoEntity);

    }

    @Test
    @DisplayName("Deveria detalhar um pedido especifico")
    void detalhaPedidoEspecificoPeloId() {
        PedidoEntity pedidoEntity = new PedidoEntity();
        Mockito.when(pedidoRepository.findById(1l)).thenReturn(Optional.of(pedidoEntity));

        pedidoService.detalha(1l);

        Mockito.verify(modelMapper).map(pedidoEntity, ResponsePedidoDTO.class);
    }

    @Test
    @DisplayName("Ao atualizar o item o total deve ser a soma dos valores dos itens")
    void deveriaAtualizaCorretamente() {
        PedidoEntity pedidoEntity = new PedidoEntity();
        RequestAtualizaItemDto itemDto = RequestAtualizaItemDto.builder()
                .nome("Carro")
                .descricao("Automóvel")
                .valor(879.58)
                .dataValidade("27/12/2011 15:10:15")
                .build();

        List<RequestAtualizaItemDto> itensDtoList = new ArrayList<>();
        itensDtoList.add(itemDto);

        RequestAtualizaPedidoDto pedidoDto = RequestAtualizaPedidoDto.builder()
                .cpf("xxx-xxx-xxx-yy")
                .itens(itensDtoList)
                .build();


        Mockito.when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoEntity));

        pedidoService.atualiza(1L, pedidoDto);

        Assertions.assertEquals(pedidoEntity.getTotal(), itemDto.getValor());
    }
}