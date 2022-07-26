package br.com.compass.Sprint05.dto.rabbitMQ;


import br.com.compass.Sprint05.dto.pagamento.response.ResponsePagamentoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoMensagemDto {
    private Long id;
    private Double total;
    private String cpf;
    private String tipoDoPagamento;
    private ResponsePagamentoDto pagamento;

}
