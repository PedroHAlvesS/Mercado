package br.com.compass.pagamento.dto.banco.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RequestCartaoCliente {
    private String numberToken;
    private String cardholderName;
    private String securityCode;
    private String brand;
    private String expirationMonth;
    private String expirationYear;

}
