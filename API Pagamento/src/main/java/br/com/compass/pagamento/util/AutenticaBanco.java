package br.com.compass.pagamento.util;

import br.com.compass.pagamento.dto.banco.request.RequestAuthDto;
import br.com.compass.pagamento.dto.banco.response.ResponseAuthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AutenticaBanco {

    @Autowired
    private WebClient.Builder webClientBuilder;


    public ResponseAuthDto autentica() {
        RequestAuthDto requestAuthDto = new RequestAuthDto();

        String urlAutentica = "https://pb-getway-payment.herokuapp.com/v1/auth";

        return webClientBuilder.build()
                .post()
                .uri(urlAutentica)
                .bodyValue(requestAuthDto)
                .retrieve()
                .bodyToMono(ResponseAuthDto.class)
                .block();
    }

}
