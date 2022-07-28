package br.com.compass.pagamento.service;

import br.com.compass.pagamento.dto.PagamentoDto;
import br.com.compass.pagamento.model.PagamentoEntity;
import br.com.compass.pagamento.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository pagamentoRepository;

    public void salva(PagamentoDto pagamentoDto) {
        PagamentoEntity pagamentoEntity = new PagamentoEntity();
        pagamentoEntity.setTotal(pagamentoDto.getTotal());
        pagamentoEntity.setPedidoId(pagamentoDto.getId());

        pagamentoRepository.save(pagamentoEntity);
    }

}
