package br.com.compass.Sprint05.util;

import br.com.compass.Sprint05.dto.item.request.RequestItemDto;
import br.com.compass.Sprint05.dto.item.request.RequestItemOfertaDto;
import br.com.compass.Sprint05.exceptions.DataValidadeInvalida;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidaDatasTest {

    @InjectMocks
    private ValidaDatas validaDatas;
    @Mock
    private ConverteDatas converteDatas;

    @Test
    @DisplayName("Data válida deveria passar")
    void validaDataDeCriacaoDaOferta() {
        // A validação é feita com base na data agora, por isso é criado uma data agora com +2 de hora;
        // para esse teste ficar permante
        LocalDateTime dataMockito = LocalDateTime.now().plusHours(2);


        RequestItemOfertaDto ofertaDto = new RequestItemOfertaDto();
        ofertaDto.setDataValidade("27/10/2010 10:10:10");
        List<RequestItemOfertaDto> ofertas = new ArrayList<>();
        ofertas.add(ofertaDto);

        RequestItemDto itemDto = RequestItemDto.builder()
                .ofertas(ofertas)
                .build();


        Mockito.when(converteDatas.formataDataISO8601(ofertaDto.getDataValidade())).thenReturn(dataMockito);
        validaDatas.validaDataDeCriacaoDaOferta(itemDto);
    }

    @Test
    @DisplayName("Data inválida deve lançar exception")
    void naoDeveriaPermitirDataInvalida() {
        // A validação é feita com base na data agora, por isso é criado uma data agora com -1 de hora;
        // para esse teste ficar permante
        LocalDateTime dataMockito = LocalDateTime.now();
        dataMockito.minusHours(1);


        RequestItemOfertaDto ofertaDto = new RequestItemOfertaDto();
        ofertaDto.setDataValidade("27/10/2010 10:10:10");
        List<RequestItemOfertaDto> ofertas = new ArrayList<>();
        ofertas.add(ofertaDto);

        RequestItemDto itemDto = RequestItemDto.builder()
                .ofertas(ofertas)
                .build();


        Mockito.when(converteDatas.formataDataISO8601(ofertaDto.getDataValidade())).thenReturn(dataMockito);
        Assertions.assertThrows(DataValidadeInvalida.class, () -> validaDatas.validaDataDeCriacaoDaOferta(itemDto));
    }
}