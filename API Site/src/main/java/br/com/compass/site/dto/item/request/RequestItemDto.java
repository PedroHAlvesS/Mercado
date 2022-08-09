package br.com.compass.site.dto.item.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class RequestItemDto {
    @NotBlank
    @Length(min = 3, max = 150)
    private String nome;
    @NotNull
    @Future
    @JsonFormat(pattern = "dd/MM/uuuu HH:mm:ss", lenient = OptBoolean.FALSE)
    private LocalDateTime dataValidade;
    @NotNull
    @Positive
    @Max(value = 1000000000)
    private Double valor;
    @NotBlank
    @Length(min = 3, max = 150)
    private String descricao;
    @NotNull
    @Positive
    @Max(value = 10000000)
    private int estoque;
}
