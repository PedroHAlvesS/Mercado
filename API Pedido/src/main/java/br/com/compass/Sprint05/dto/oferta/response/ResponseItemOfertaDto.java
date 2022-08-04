package br.com.compass.Sprint05.dto.oferta.response;

import br.com.compass.Sprint05.dto.oferta.request.RequestItemOfertaDto;
import lombok.Data;

@Data
public class ResponseItemOfertaDto extends RequestItemOfertaDto {
    private String dataCriacao;
}
