package br.com.compass.site.services;

import br.com.compass.site.dto.item.request.RequestItemDto;
import br.com.compass.site.dto.item.response.ResponseItemDto;
import br.com.compass.site.entities.ItemEntity;
import br.com.compass.site.repository.ItemRepository;
import br.com.compass.site.util.GerarSkuId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;
    private final GerarSkuId gerarSkuId;
    public ResponseItemDto criaItem(RequestItemDto requestDto) {
        log.debug("Criando novo item, request:{}", requestDto);
        ItemEntity itemEntity = modelMapper.map(requestDto, ItemEntity.class);
        itemEntity.setSkuId(gerarSkuId.gerarSkuId(requestDto));
        ItemEntity saveEntity = itemRepository.save(itemEntity);
        log.debug("Item criado e salvo:{}", saveEntity);
        return modelMapper.map(saveEntity, ResponseItemDto.class);
    }

    public List<ResponseItemDto> listaItens() {
        log.debug("Listando itens");
        List<ItemEntity> itemEntityList = itemRepository.findAll();
        List<ResponseItemDto> responseItemDtoList = itemEntityList.stream().map(itemEntity -> modelMapper.map(itemEntity, ResponseItemDto.class)).collect(Collectors.toList());
        return responseItemDtoList;
    }
}
