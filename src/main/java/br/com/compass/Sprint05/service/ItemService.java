package br.com.compass.Sprint05.service;

import br.com.compass.Sprint05.dto.item.RequestAtualizaItemDto;
import br.com.compass.Sprint05.dto.item.ResponseItemDto;
import br.com.compass.Sprint05.entities.ItemEntity;
import br.com.compass.Sprint05.exceptions.ItemNaoEncontrado;
import br.com.compass.Sprint05.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ItemRepository itemRepository;


    public ResponseItemDto atualiza(Long id, RequestAtualizaItemDto patchDto) {
        ItemEntity itemEntity = itemRepository.findById(id).orElseThrow(ItemNaoEncontrado::new);
        modelMapper.map(patchDto, itemEntity);
        itemRepository.save(itemEntity);
        return modelMapper.map(itemEntity, ResponseItemDto.class);
    }
}
