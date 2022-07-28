package br.com.compass.Sprint05.dto.rabbitMQ;


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
