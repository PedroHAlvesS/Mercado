package br.com.compass.pagamento.dto.banco.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RequestAuthDto {
    private String clientId = "client_id_pedro";
    private String apiKey = "adde23eb-2bc3-437f-bdba-f8f96f8c4014";
}
