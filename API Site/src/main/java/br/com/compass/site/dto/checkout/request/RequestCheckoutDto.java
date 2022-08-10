package br.com.compass.site.dto.checkout.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RequestCheckoutDto {
    private List<@Valid RequestCheckoutItensDto> itens;
    @JsonProperty("cliente_info")
    @NotNull
    private @Valid RequestCheckoutClienteDto clienteInfo;
}
