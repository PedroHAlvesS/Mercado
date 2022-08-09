package br.com.compass.site.dto.cliente.request;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RequestClienteDto {
    @CPF
    @NotBlank
    private String cpf;
    @NotBlank
    private String nome;
}
