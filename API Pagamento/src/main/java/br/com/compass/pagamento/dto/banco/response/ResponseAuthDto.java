package br.com.compass.pagamento.dto.banco.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseAuthDto {
    @JsonProperty("access_token")
    private String acessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private String expiresIn;


}
