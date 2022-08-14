package br.com.compass.Sprint05.service;

import br.com.compass.Sprint05.constants.EnumStatusPagamento;
import br.com.compass.Sprint05.dto.item.request.RequestAtualizaItemDto;
import br.com.compass.Sprint05.dto.item.request.RequestItemDto;
import br.com.compass.Sprint05.dto.oferta.request.RequestItemOfertaDto;
import br.com.compass.Sprint05.dto.pagamento.request.RequestPagamentoDto;
import br.com.compass.Sprint05.dto.pedido.request.RequestAtualizaPedidoDto;
import br.com.compass.Sprint05.dto.pedido.request.RequestPedidoDto;
import br.com.compass.Sprint05.dto.pedido.response.ResponsePedidoDTO;
import br.com.compass.Sprint05.entities.PedidoEntity;
import br.com.compass.Sprint05.exceptions.PedidoJaProcessado;
import br.com.compass.Sprint05.repository.PedidoRepository;
import br.com.compass.Sprint05.util.ValidaConstants;
import br.com.compass.Sprint05.util.ValidaDatas;
import br.com.compass.Sprint05.util.ValidaValorCartao;
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

    @Mock
    private ValidaConstants validaConstants;

    @Mock
    private ValidaValorCartao validaValorCartao;


    @Test
    @DisplayName("Deve salvar um item, além de o total ser a soma do valor de cada item")
    void deveriaSalvarUmPedidoCorretamente() {
        RequestItemDto itemDto = RequestItemDto.builder()
                .nome("Carro")
                .descricao("Automóvel")
                .valor(879.58)
                .qtd(2)
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

        RequestPagamentoDto pagamentoDto = new RequestPagamentoDto();

        pedidoDto.setPagamento(pagamentoDto);


        Mockito.when(modelMapper.map(pedidoDto, PedidoEntity.class)).thenReturn(pedidoEntity);
        Mockito.when(pedidoRepository.save(pedidoEntity)).thenReturn(pedidoEntity);

        pedidoService.salva(pedidoDto);
        Assertions.assertEquals(itemDto.getValor() * itemDto.getQtd(), pedidoEntity.getTotal());
        Mockito.verify(modelMapper).map(pedidoEntity, ResponsePedidoDTO.class);

    }

    @Test
    @DisplayName("Deveria salvar um pedido aplicando o valor da oferta. Sem que essa oferta faca o item valer 100 reais")
    void deveriaSalvarUmPedidoAplicandoOValorDaOfertaSemSuperarOValorMinimoDe100() {
        RequestItemOfertaDto ofertaDto = RequestItemOfertaDto.builder()
                .nome("Oferta teste")
                .desconto(899.99)
                .build();

        List<RequestItemOfertaDto> ofertaDtoList = new ArrayList<>();
        ofertaDtoList.add(ofertaDto);

        int itemQtd = 2;
        RequestItemDto itemDto = RequestItemDto.builder()
                .nome("Carro")
                .descricao("Automóvel")
                .valor(1000.00)
                .qtd(itemQtd)
                .dataValidade("27/12/2011 15:10:15")
                .ofertas(ofertaDtoList)
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

        RequestPagamentoDto pagamentoDto = new RequestPagamentoDto();
        pedidoDto.setPagamento(pagamentoDto);


        Mockito.when(modelMapper.map(pedidoDto, PedidoEntity.class)).thenReturn(pedidoEntity);
        Mockito.when(pedidoRepository.save(pedidoEntity)).thenReturn(pedidoEntity);
        Double valorEsperado = ((itemDto.getValor() - ofertaDto.getDesconto()) * itemQtd) ;


        pedidoService.salva(pedidoDto);
        Assertions.assertEquals(valorEsperado, pedidoEntity.getTotal());
        Mockito.verify(modelMapper).map(pedidoEntity, ResponsePedidoDTO.class);
    }

    @Test
    @DisplayName("Deveria salvar o pedido com valor total de 100, caso o desconto supere o valor do item")
    void deveriaSalvarOPedidoComValorTotalDe100CasoODescontoSupereOValorDoItem() {
        RequestItemOfertaDto ofertaDto = RequestItemOfertaDto.builder()
                .nome("Oferta teste")
                .desconto(1.0)
                .build();

        RequestItemOfertaDto ofertaDto2 = RequestItemOfertaDto.builder()
                .nome("Oferta teste 2")
                .desconto(999.0)
                .build();

        List<RequestItemOfertaDto> ofertaDtoList = new ArrayList<>();
        ofertaDtoList.add(ofertaDto);
        ofertaDtoList.add(ofertaDto2);

        RequestItemDto itemDto = RequestItemDto.builder()
                .nome("Carro")
                .descricao("Automóvel")
                .qtd(1)
                .valor(1000.00)
                .dataValidade("27/12/2011 15:10:15")
                .ofertas(ofertaDtoList)
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

        RequestPagamentoDto pagamentoDto = new RequestPagamentoDto();
        pedidoDto.setPagamento(pagamentoDto);


        Mockito.when(modelMapper.map(pedidoDto, PedidoEntity.class)).thenReturn(pedidoEntity);
        Mockito.when(pedidoRepository.save(pedidoEntity)).thenReturn(pedidoEntity);
        Double valorEsperado = 100.0;


        pedidoService.salva(pedidoDto);
        Assertions.assertEquals(valorEsperado, pedidoEntity.getTotal());
        Mockito.verify(modelMapper).map(pedidoEntity, ResponsePedidoDTO.class);
    }

    @Test
    @DisplayName("Deveria deletar com sucesso um pedido em processando")
    void deveriaDeletarComSucesso() {
        PedidoEntity pedidoEntity = new PedidoEntity();
        Mockito.when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoEntity));

        pedidoService.deleta(1L);
        Mockito.verify(pedidoRepository).delete(pedidoEntity);
    }

    @Test
    @DisplayName("Nao deveria deletar um pedido ja processado (Aprovado ou Rejeitado)")
    void naoDeveriaDeletarUmPedidoQuandoSeuStatusPagamentoForAprovadoOuRejeitado() {
        PedidoEntity pedidoEntityRejeitado = new PedidoEntity();
        pedidoEntityRejeitado.setStatusDoPagamento(EnumStatusPagamento.REJEITADO);
        PedidoEntity pedidoEntityAprovado = new PedidoEntity();
        pedidoEntityAprovado.setStatusDoPagamento(EnumStatusPagamento.APROVADO);

        Mockito.when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoEntityRejeitado));
        Mockito.when(pedidoRepository.findById(2L)).thenReturn(Optional.of(pedidoEntityAprovado));

        Assertions.assertThrows(PedidoJaProcessado.class, () -> pedidoService.deleta(1L));
        Assertions.assertThrows(PedidoJaProcessado.class, () -> pedidoService.deleta(2L));
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