package br.com.compass.pagamento.dto.banco.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RequestClienteBancoDto {
    private String documentType;
    private String documentNumber;
}
