package br.com.compass.site.dto.item.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
public class RequestItemDto {
    @NotBlank
    private String nome;
    @NotNull
    @Future
    @JsonFormat(pattern = "dd/MM/uuuu HH:mm:ss", lenient = OptBoolean.FALSE)
    private LocalDateTime dataValidade;
    @NotNull
    @Positive
    private Double valor;
    @NotBlank
    private String descricao;
    @NotNull
    @Positive
    private int estoque;
}
