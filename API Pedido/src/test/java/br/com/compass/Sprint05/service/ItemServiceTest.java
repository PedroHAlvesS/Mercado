package br.com.compass.Sprint05.service;

import br.com.compass.Sprint05.dto.item.request.RequestAtualizaItemDto;
import br.com.compass.Sprint05.entities.ItemEntity;
import br.com.compass.Sprint05.exceptions.ItemNaoEncontrado;
import br.com.compass.Sprint05.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ModelMapper modelMapper;


    @Test
    @DisplayName("Deveria atualizar um item")
    void deveriaAtualizarUmItem() {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(1L);
        RequestAtualizaItemDto itemDto = new RequestAtualizaItemDto();

        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(itemEntity));

        itemService.atualiza(1L, itemDto);

        Mockito.verify(itemRepository).save(itemEntity);

    }

    @Test
    @DisplayName("Deveria lancar uma exception ao atualizar um item nao existente")
    void deveriaLancarExceptionAoBuscarItemNaoExistente() {

        RequestAtualizaItemDto itemDto = new RequestAtualizaItemDto();

        Assertions.assertThrows(ItemNaoEncontrado.class, () -> itemService.atualiza(2L, itemDto));
    }
}