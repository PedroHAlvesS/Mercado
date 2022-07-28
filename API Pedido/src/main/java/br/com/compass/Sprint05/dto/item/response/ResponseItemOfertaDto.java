package br.com.compass.Sprint05.dto.item.response;

import br.com.compass.Sprint05.dto.item.request.RequestItemOfertaDto;
import lombok.Data;

@Data
public class ResponseItemOfertaDto extends RequestItemOfertaDto {
    private String dataCriacao;
}
