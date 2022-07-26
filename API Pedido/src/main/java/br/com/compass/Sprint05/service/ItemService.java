package br.com.compass.Sprint05.service;

import br.com.compass.Sprint05.dto.item.request.RequestAtualizaItemDto;
import br.com.compass.Sprint05.dto.item.response.ResponseItemDto;
import br.com.compass.Sprint05.entities.ItemEntity;
import br.com.compass.Sprint05.exceptions.ItemNaoEncontrado;
import br.com.compass.Sprint05.repository.ItemRepository;
import br.com.compass.Sprint05.util.ValidaDatas;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ValidaDatas validaDatas;


    public ResponseItemDto atualiza(Long id, RequestAtualizaItemDto patchDto) {
        ItemEntity itemEntity = itemRepository.findById(id).orElseThrow(ItemNaoEncontrado::new);
        modelMapper.map(patchDto, itemEntity);
        itemRepository.save(itemEntity);
        return modelMapper.map(itemEntity, ResponseItemDto.class);
    }

}
