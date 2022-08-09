package br.com.compass.site.dto.item.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseItemDto {
    private Long id;
    private String nome;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", lenient = OptBoolean.FALSE)
    private LocalDateTime dataValidade;
    private Double valor;
    private String descricao;
    private int estoque;
    private String skuId;
}
