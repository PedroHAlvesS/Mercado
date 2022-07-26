package br.com.compass.Sprint05.service;

import br.com.compass.Sprint05.dto.item.request.RequestAtualizaItemDto;
import br.com.compass.Sprint05.dto.item.response.ResponseItemDto;
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
        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setNome("Teste data");
        itemRepository.save(itemEntity1);
        return modelMapper.map(itemEntity, ResponseItemDto.class);
    }
}
