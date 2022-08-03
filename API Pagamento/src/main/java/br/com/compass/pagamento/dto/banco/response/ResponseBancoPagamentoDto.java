package br.com.compass.pagamento.dto.banco.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseBancoPagamentoDto {
    @JsonProperty("payment_id")
    private String paymentId;
    @JsonProperty("seller_id")
    private String sellerId;
    @JsonProperty("transaction_amout")
    private Double transactionAmount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("status")
    private String status;
    @JsonProperty("received_at")
    private LocalDateTime receivedAt;
    @JsonProperty("authorization")
    private ResponseAuthPagamentoDto authorization;

}
