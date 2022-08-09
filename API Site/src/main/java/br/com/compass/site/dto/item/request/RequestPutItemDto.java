package br.com.compass.site.dto.item.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
public class RequestPutItemDto {
    @NotNull
    @Positive
    @Max(value = 1000000000)
    private Double valor;
    @NotNull
    @Positive
    @Max(value = 10000000)
    private int estoque;
    @NotNull
    @JsonFormat(pattern = "dd/MM/uuuu HH:mm:ss", lenient = OptBoolean.FALSE)
    private LocalDateTime dataValidade;

}
