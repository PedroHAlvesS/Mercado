package br.com.compass.Sprint05.service;

import br.com.compass.Sprint05.dto.pedido.request.RequestAtualizaPedidoDto;
import br.com.compass.Sprint05.dto.pedido.request.RequestPedidoDto;
import br.com.compass.Sprint05.dto.pedido.response.ResponsePedidoDTO;
import br.com.compass.Sprint05.exceptions.PedidoNaoEncontrado;
import br.com.compass.Sprint05.models.PedidoEntity;
import br.com.compass.Sprint05.repository.ItemRepository;
import br.com.compass.Sprint05.repository.PedidoRepository;
import br.com.compass.Sprint05.util.ValidaConstants;
import br.com.compass.Sprint05.util.ValidaDatas;
import br.com.compass.Sprint05.util.ValidaValorCartao;
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
    private ValidaDatas validaDatas;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ValidaConstants validaConstants;

    @Autowired
    private ValidaValorCartao validaValorCartao;

    public ResponsePedidoDTO salva(RequestPedidoDto requestDTO) {

        Double valor = calculaTotal(requestDTO);

        validaDataOferta(requestDTO);

        validaConstants.validaTipoDoPagamento(requestDTO);

        validaConstants.validaMarcaCartao(requestDTO);
        validaConstants.validaTipoDeMoeda(requestDTO);

        PedidoEntity pedidoEntity = modelMapper.map(requestDTO, PedidoEntity.class);

        validaValorCartao.validaValorDoCartao(valor, requestDTO.getPagamento().getValor());

        pedidoEntity.setTotal(valor);
        PedidoEntity saveEntity = pedidoRepository.save(pedidoEntity);
        return modelMapper.map(saveEntity, ResponsePedidoDTO.class);

    }

    private void validaDataOferta(RequestPedidoDto requestDTO) {
        for (int i = 0; i < requestDTO.getItens().size(); i++) {
            validaDatas.validaDataDeCriacaoDaOferta(requestDTO.getItens().get(i));
        }
    }

    private Double calculaTotal(RequestPedidoDto requestDTO) {
        Double valor = 0.0;
        for (int i = 0; i < requestDTO.getItens().size(); i++) {
            valor += requestDTO.getItens().get(i).getValor();
        }
        return valor;
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
        return pedidoEntities.map(pedidoEntity -> modelMapper.map(pedidoEntity, ResponsePedidoDTO.class));
    }


    public ResponsePedidoDTO detalha(Long id) {
        PedidoEntity pedidoEntity = pedidoRepository.findById(id).orElseThrow(PedidoNaoEncontrado::new);
        return modelMapper.map(pedidoEntity, ResponsePedidoDTO.class);
    }

    public ResponsePedidoDTO atualiza(Long id, RequestAtualizaPedidoDto patchDto) {
        PedidoEntity pedidoEntity = pedidoRepository.findById(id).orElseThrow(PedidoNaoEncontrado::new);
        modelMapper.map(patchDto, pedidoEntity);

        if (patchDto.getItens() != null && !patchDto.getItens().isEmpty()) {
            Double total = 0.0;
            for (int i = 0; i < patchDto.getItens().size(); i++) {
                total += patchDto.getItens().get(i).getValor();
            }
            pedidoEntity.setTotal(total);
        }
        pedidoRepository.save(pedidoEntity);
        return modelMapper.map(pedidoEntity, ResponsePedidoDTO.class);
    }
}
