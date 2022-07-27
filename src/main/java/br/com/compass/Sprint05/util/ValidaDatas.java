package br.com.compass.Sprint05.util;

import br.com.compass.Sprint05.dto.item.request.RequestItemDto;
import br.com.compass.Sprint05.exceptions.DataValidadeInvalida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidaDatas {

    @Autowired
    private ConverteDatas converteDatas;

    public void validaDataDeCriacaoDaOferta(RequestItemDto requestItemDto) {
        for (int i = 0; i < requestItemDto.getOfertas().size(); i++) {
            LocalDateTime dataAtual = LocalDateTime.now();
            LocalDateTime dataValidadeOferta = converteDatas.formataDataISO8601(requestItemDto.getOfertas().get(i).getDataValidade());
            if (dataValidadeOferta.isBefore(dataAtual)) {
                throw new DataValidadeInvalida();
            }
        }
    }
}
