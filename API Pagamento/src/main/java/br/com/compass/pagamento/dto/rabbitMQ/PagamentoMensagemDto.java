package br.com.compass.pagamento.dto.rabbitMQ;


import br.com.compass.pagamento.dto.pagamento.ResponsePagamentoDto;
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
