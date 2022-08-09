package br.com.compass.site.dto.cliente.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseClienteDto {
    private String cpf;
    private String nome;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", lenient = OptBoolean.FALSE)
    private LocalDateTime dataCriacao;
}
