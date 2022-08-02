package br.com.compass.pagamento.service;

import br.com.compass.pagamento.dto.rabbitMQ.PagamentoMensagemDto;
import br.com.compass.pagamento.model.PagamentoEntity;
import br.com.compass.pagamento.repository.PagamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void salva(PagamentoMensagemDto pagamentoDto) {
        PagamentoEntity pagamentoEntity = modelMapper.map(pagamentoDto, PagamentoEntity.class);
        pagamentoRepository.save(pagamentoEntity);
    }

}
