package br.com.compass.Sprint05.service;

import br.com.compass.Sprint05.dto.oferta.request.RequestOfertaDto;
import br.com.compass.Sprint05.dto.oferta.response.ResponseOfertaDto;
import br.com.compass.Sprint05.exceptions.DataValidadeInvalida;
import br.com.compass.Sprint05.models.ItemEntity;
import br.com.compass.Sprint05.models.OfertaEntity;
import br.com.compass.Sprint05.repository.OfertaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;
    @Autowired
    private ModelMapper modelMapper;
    public ResponseOfertaDto cria(RequestOfertaDto requestDto) {
        OfertaEntity ofertaEntity = modelMapper.map(requestDto, OfertaEntity.class);
        LocalDateTime dataAtual = LocalDateTime.now();
        if (ofertaEntity.getDataValidade().isBefore(dataAtual)) {
            throw new DataValidadeInvalida();
        }
        ofertaRepository.save(ofertaEntity);
        return modelMapper.map(ofertaEntity, ResponseOfertaDto.class);
    }
}
