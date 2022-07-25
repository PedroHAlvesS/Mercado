package br.com.compass.Sprint05.service;

import br.com.compass.Sprint05.dto.request.PedidoRequestDTO;
import br.com.compass.Sprint05.dto.response.ResponsePedidoDTO;
import br.com.compass.Sprint05.entities.ItemEntity;
import br.com.compass.Sprint05.entities.PedidoEntity;
import br.com.compass.Sprint05.exceptions.ItemNaoEncontrado;
import br.com.compass.Sprint05.repository.ItemRepository;
import br.com.compass.Sprint05.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemRepository itemRepository;

    public ResponsePedidoDTO salva(PedidoRequestDTO requestDTO) {
        Double valor = 0.0;
        List<ItemEntity> itemEntityList = new ArrayList<>();
        for (int i = 0; i < requestDTO.getItens().size(); i++) {
            Long itemId = requestDTO.getItens().get(i).getItemId();
            ItemEntity item = itemRepository.findById(itemId).orElseThrow(ItemNaoEncontrado::new);
            valor += item.getValor();
            itemEntityList.add(item);
        }
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setCpf(requestDTO.getCpf());
        pedidoEntity.setItens(itemEntityList);
        pedidoEntity.setTotal(valor);
        pedidoRepository.save(pedidoEntity);
        ResponsePedidoDTO responseDTO = new ResponsePedidoDTO();
        responseDTO.setId(pedidoEntity.getId());
        responseDTO.setCpf(pedidoEntity.getCpf());
        responseDTO.setTotal(pedidoEntity.getTotal());
        return responseDTO;

    }
}
