package br.com.compass.pagamento.dto.banco.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RequestBancoPagamentoDto {
    private String sellerId;
    private RequestClienteBancoDto customer;
    private String paymentType;
    private String currency;
    private Double transactionAmount;
    private RequestCartaoCliente card;
}
