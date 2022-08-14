package br.com.compass.site.controller;

import br.com.compass.site.dto.checkout.request.RequestCheckoutDto;
import br.com.compass.site.dto.checkout.response.ResponseCheckoutDto;
import br.com.compass.site.services.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;

@Controller
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseCheckoutDto> criaCheckoutDosItens(@Valid @RequestBody RequestCheckoutDto requestDto) {
        ResponseCheckoutDto responseDto = checkoutService.enviaPedido(requestDto);
        URI uri = URI.create("http://localhost:8080/api/pedido/" + responseDto.getNumeroDoPedido());
        return ResponseEntity.created(uri).body(responseDto);
    }
}
