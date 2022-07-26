package br.com.compass.Sprint05.service;

import br.com.compass.Sprint05.dto.pedido.request.RequestPedidoDto;
import br.com.compass.Sprint05.dto.pedido.request.RequestPatchDto;
import br.com.compass.Sprint05.dto.pedido.response.ResponsePedidoDTO;
import br.com.compass.Sprint05.dto.pedido.response.ResponsePedidoDetalhadoDto;
import br.com.compass.Sprint05.models.ItemEntity;
import br.com.compass.Sprint05.models.PedidoEntity;
import br.com.compass.Sprint05.exceptions.ItemNaoEncontrado;
import br.com.compass.Sprint05.exceptions.PedidoNaoEncontrado;
import br.com.compass.Sprint05.repository.ItemRepository;
import br.com.compass.Sprint05.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponsePedidoDTO salva(RequestPedidoDto requestDTO) {
        Double valor = 0.0;
        PedidoEntity pedidoEntity = modelMapper.map(requestDTO, PedidoEntity.class);
        for (int i = 0; i < requestDTO.getItens().size(); i++) {
            ItemEntity item = itemRepository.findById(requestDTO.getItens().get(i).getItemId()).orElseThrow(ItemNaoEncontrado::new);
            valor += item.getValor();
        }
        pedidoEntity.setTotal(valor);
        PedidoEntity saveEntity = pedidoRepository.save(pedidoEntity);
        ResponsePedidoDTO responseDTO = modelMapper.map(saveEntity, ResponsePedidoDTO.class);
        return responseDTO;

    }
    public void deleta(Long id) {
        PedidoEntity pedidoEntity = pedidoRepository.findById(id).orElseThrow(PedidoNaoEncontrado::new);
        pedidoRepository.delete(pedidoEntity);
    }

    public Page<ResponsePedidoDTO> lista(String cpf, Pageable pageable) {
        Page<PedidoEntity> pedidoEntities;
        if (cpf == null) {
            pedidoEntities = pedidoRepository.findAll(pageable);
        } else {
            pedidoEntities = pedidoRepository.findByCpf(cpf, pageable);
        }
        Page<ResponsePedidoDTO> dtoPage = pedidoEntities.map(pedidoEntity -> modelMapper.map(pedidoEntity, ResponsePedidoDTO.class));
        return dtoPage;
    }


    public ResponsePedidoDetalhadoDto detalha(Long id) {
        PedidoEntity pedidoEntity = pedidoRepository.findById(id).orElseThrow(PedidoNaoEncontrado::new);
        return modelMapper.map(pedidoEntity, ResponsePedidoDetalhadoDto.class);
    }

    public ResponsePedidoDTO atualiza(Long id, RequestPatchDto patchDto) {
        PedidoEntity pedidoEntity = pedidoRepository.findById(id).orElseThrow(PedidoNaoEncontrado::new);
        modelMapper.map(patchDto, pedidoEntity);
        pedidoRepository.save(pedidoEntity);
        return modelMapper.map(pedidoEntity, ResponsePedidoDTO.class);
    }
}
