package br.com.compass.pagamento.rabbitMQ;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoMensagemDto {
    private Long id;
    private Double total;

}
