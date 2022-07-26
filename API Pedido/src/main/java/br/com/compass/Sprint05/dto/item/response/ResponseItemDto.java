package br.com.compass.Sprint05.dto.item.response;

import br.com.compass.Sprint05.dto.oferta.response.ResponseItemOfertaDto;
import lombok.Data;

import java.util.List;

@Data
public class ResponseItemDto {
    private Long id;
    private String nome;
    private String descricao;
    private String dataCriacao;
    private String dataValidade;
    private Double valor;
    private int qtd;
    private List<ResponseItemOfertaDto> ofertas;
}
