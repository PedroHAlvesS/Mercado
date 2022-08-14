package br.com.compass.site.services;

import br.com.compass.site.builders.RequestItemDtoBuilder;
import br.com.compass.site.dto.item.request.RequestItemDto;
import br.com.compass.site.dto.item.request.RequestPutItemDto;
import br.com.compass.site.dto.item.response.ResponseItemDto;
import br.com.compass.site.entities.ItemEntity;
import br.com.compass.site.repository.ItemRepository;
import br.com.compass.site.util.GerarSkuId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = ItemService.class)
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private GerarSkuId gerarSkuId;

    @SpyBean
    private ModelMapper modelMapper;


    @Test
    @DisplayName("deveria criar um item de acordo com os parametros informado do DTO")
    void deveriaCriarUmitemDeAcordoComOsParamentrosDoDto() {
        RequestItemDto itemDto = RequestItemDtoBuilder.one().now();
        ItemEntity itemEntity = criaItemEntity(1L, "abc123",
                itemDto.getNome(), itemDto.getDescricao(), itemDto.getValor(),
                itemDto.getEstoque(), itemDto.getDataValidade());

        Mockito.when(gerarSkuId.gerarSkuId(itemDto)).thenReturn("abc123");
        Mockito.when(itemRepository.save(any())).thenReturn(itemEntity);

        ResponseItemDto responseItemDto = itemService.criaItem(itemDto);

        Assertions.assertEquals(itemEntity.getId(), responseItemDto.getId());
        Assertions.assertEquals(itemEntity.getNome(), responseItemDto.getNome());
        Assertions.assertEquals(itemEntity.getDescricao(), responseItemDto.getDescricao());
        Assertions.assertEquals(itemEntity.getEstoque(), responseItemDto.getEstoque());
        Assertions.assertEquals(itemEntity.getValor(), responseItemDto.getValor());
        Assertions.assertEquals(itemEntity.getDataValidade(), responseItemDto.getDataValidade());
        Assertions.assertEquals("abc123", responseItemDto.getSkuId());
    }

    @Test
    @DisplayName("deveria listar todos os itens")
    void deveriaListarTodosOsItens() {
        RequestItemDto itemDto = RequestItemDtoBuilder.one().now();
        ItemEntity itemEntity1 = criaItemEntity(1L, "abc123",
                itemDto.getNome(), itemDto.getDescricao(), itemDto.getValor(),
                itemDto.getEstoque(), itemDto.getDataValidade());
        ItemEntity itemEntity2 = criaItemEntity(2L, "abc123",
                itemDto.getNome(), itemDto.getDescricao(), itemDto.getValor(),
                itemDto.getEstoque(), itemDto.getDataValidade());

        List<ItemEntity> itemEntityList = new ArrayList<>();
        itemEntityList.add(itemEntity1);
        itemEntityList.add(itemEntity2);

        Mockito.when(itemRepository.findAll()).thenReturn(itemEntityList);

        List<ResponseItemDto> responseItemDtoList = itemService.listaItens();

        for (int i = 0; i < itemEntityList.size(); i++) {
            Assertions.assertEquals(itemEntityList.get(i).getId(), responseItemDtoList.get(i).getId());
            Assertions.assertEquals(itemEntityList.get(i).getNome(), responseItemDtoList.get(i).getNome());
            Assertions.assertEquals(itemEntityList.get(i).getDescricao(), responseItemDtoList.get(i).getDescricao());
            Assertions.assertEquals(itemEntityList.get(i).getEstoque(), responseItemDtoList.get(i).getEstoque());
            Assertions.assertEquals(itemEntityList.get(i).getValor(), responseItemDtoList.get(i).getValor());
            Assertions.assertEquals(itemEntityList.get(i).getDataValidade(), responseItemDtoList.get(i).getDataValidade());
            Assertions.assertEquals(itemEntityList.get(i).getSkuId(), responseItemDtoList.get(i).getSkuId());
        }
        Assertions.assertEquals(itemEntityList.size(), responseItemDtoList.size());
    }

    @Test
    @DisplayName("Deveria listar o item individual")
    void deveriaListaItemDeFormaUnitaria() {
        RequestItemDto itemDto = RequestItemDtoBuilder.one().now();
        ItemEntity itemEntity = criaItemEntity(1L, "abc123",
                itemDto.getNome(), itemDto.getDescricao(), itemDto.getValor(),
                itemDto.getEstoque(), itemDto.getDataValidade());

        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(itemEntity));

        ResponseItemDto responseItemDto = itemService.listaItem(1L);

        Assertions.assertEquals(itemEntity.getId(), responseItemDto.getId());
        Assertions.assertEquals(itemEntity.getNome(), responseItemDto.getNome());
        Assertions.assertEquals(itemEntity.getDescricao(), responseItemDto.getDescricao());
        Assertions.assertEquals(itemEntity.getEstoque(), responseItemDto.getEstoque());
        Assertions.assertEquals(itemEntity.getValor(), responseItemDto.getValor());
        Assertions.assertEquals(itemEntity.getDataValidade(), responseItemDto.getDataValidade());
        Assertions.assertEquals("abc123", responseItemDto.getSkuId());
    }

    @Test
    @DisplayName("deveria lancar exception ao buscar um id nao existente")
    void deveriaLancarUmaExceptionAoListaUmItemPorIdNaoExistente() {
        RequestItemDto itemDto = RequestItemDtoBuilder.one().now();
        ItemEntity itemEntity = criaItemEntity(1L, "abc123",
                itemDto.getNome(), itemDto.getDescricao(), itemDto.getValor(),
                itemDto.getEstoque(), itemDto.getDataValidade());

        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(itemEntity));

        Assertions.assertThrows(ResponseStatusException.class, () -> itemService.listaItem(5L));
    }

    @Test
    @DisplayName("deveria atualizar um item (estoque, valor e data validade)")
    void deveriaAtualizarUmItem() {
        RequestItemDto itemDto = RequestItemDtoBuilder.one().now();
        ItemEntity itemEntity = criaItemEntity(1L, "abc123",
                itemDto.getNome(), itemDto.getDescricao(), itemDto.getValor(),
                itemDto.getEstoque(), itemDto.getDataValidade());

        RequestPutItemDto putItemDto = new RequestPutItemDto();
        putItemDto.setValor(8000.0);
        putItemDto.setEstoque(1);
        putItemDto.setDataValidade(LocalDateTime.now().plusHours(2));


        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(itemEntity));

        itemService.atualizaItem(1L, putItemDto);

        Mockito.verify(itemRepository).save(itemEntity);

        Assertions.assertEquals(putItemDto.getValor(), itemEntity.getValor());
        Assertions.assertEquals(putItemDto.getEstoque(), itemEntity.getEstoque());
        Assertions.assertEquals(putItemDto.getDataValidade(), itemEntity.getDataValidade());
        Assertions.assertEquals(itemDto.getNome(), itemEntity.getNome());
        Assertions.assertEquals(itemDto.getDescricao(), itemEntity.getDescricao());
        Assertions.assertEquals("abc123", itemEntity.getSkuId());
    }

    @Test
    @DisplayName("deveria lancar exception ao buscar um id nao existente")
    void deveriaLancarUmaExceptionAoAtualizarUmItemPorIdNaoExistente() {
        RequestItemDto itemDto = RequestItemDtoBuilder.one().now();
        ItemEntity itemEntity = criaItemEntity(1L, "abc123",
                itemDto.getNome(), itemDto.getDescricao(), itemDto.getValor(),
                itemDto.getEstoque(), itemDto.getDataValidade());
        RequestPutItemDto putItemDto = new RequestPutItemDto();

        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(itemEntity));

        Assertions.assertThrows(ResponseStatusException.class, () -> itemService.atualizaItem(5L, putItemDto));
    }


    private ItemEntity criaItemEntity(Long id, String skuId, String nome, String descricao, Double valor, int estoque, LocalDateTime data) {
        return new ItemEntity(id, skuId, nome, descricao, valor, estoque, data);
    }
}