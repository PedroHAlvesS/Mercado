package br.com.compass.site.dto.item.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestPutItemDto {
    private Double valor;
    private int estoque;
    @JsonFormat(pattern = "dd/MM/uuuu HH:mm:ss", lenient = OptBoolean.FALSE)
    private LocalDateTime dataValidade;

}
