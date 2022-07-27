package br.com.compass.Sprint05.service;

import br.com.compass.Sprint05.dto.item.request.RequestAtualizaItemDto;
import br.com.compass.Sprint05.dto.item.request.RequestItemDto;
import br.com.compass.Sprint05.dto.item.response.ResponseItemDto;
import br.com.compass.Sprint05.exceptions.ItemNaoEncontrado;
import br.com.compass.Sprint05.exceptions.OfertaNaoEncontrado;
import br.com.compass.Sprint05.models.ItemEntity;
import br.com.compass.Sprint05.models.OfertaEntity;
import br.com.compass.Sprint05.repository.ItemRepository;
import br.com.compass.Sprint05.repository.OfertaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OfertaRepository ofertaRepository;


    public ResponseItemDto atualiza(Long id, RequestAtualizaItemDto patchDto) {
        ItemEntity itemEntity = itemRepository.findById(id).orElseThrow(ItemNaoEncontrado::new);
        modelMapper.map(patchDto, itemEntity);
        itemRepository.save(itemEntity);
        return modelMapper.map(itemEntity, ResponseItemDto.class);
    }

    public ResponseItemDto cria(RequestItemDto requestItemDto) {
        ItemEntity itemEntity = modelMapper.map(requestItemDto, ItemEntity.class);

        if (requestItemDto.getOfertas() != null) {
            List<OfertaEntity> ofertasValidas = new ArrayList<>();

            // Loop para validar se a oferta já tem data vencida
            for (int i = 0; i < requestItemDto.getOfertas().size(); i++) {
                LocalDateTime dataAtual = LocalDateTime.now();
                OfertaEntity oferta = ofertaRepository.findById(requestItemDto.getOfertas().get(i).getOfertaId()).orElseThrow(OfertaNaoEncontrado::new);
                if (oferta.getDataValidade().isAfter(dataAtual)) {
                    ofertasValidas.add(oferta);
                }
            }
            itemEntity.setOfertas(ofertasValidas);
        }
        ItemEntity itemSaved = itemRepository.save(itemEntity);
        return modelMapper.map(itemSaved, ResponseItemDto.class);
    }
}
