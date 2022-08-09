package br.com.compass.site.dto.cartoes.response;

import lombok.Data;

@Data
public class ResponseCartoesDto {
    private Long id;
    private String numero;
    private String codigo;
    private String mesValidade;
    private String anoValidade;
    private String marca;
}
