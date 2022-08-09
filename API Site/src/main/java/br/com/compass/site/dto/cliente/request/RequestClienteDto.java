package br.com.compass.site.dto.cliente.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;

@Data
public class RequestClienteDto {
    @CPF
    @NotBlank
    private String cpf;
    @NotBlank
    @Length(min = 3)
    private String nome;
}
