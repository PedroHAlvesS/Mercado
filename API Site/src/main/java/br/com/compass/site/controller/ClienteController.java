package br.com.compass.site.controller;

import br.com.compass.site.dto.cartoes.request.RequestCartoesDto;
import br.com.compass.site.dto.cartoes.response.ResponseCartoesDto;
import br.com.compass.site.dto.cliente.request.RequestClienteDto;
import br.com.compass.site.dto.cliente.request.RequestPutClienteDto;
import br.com.compass.site.dto.cliente.response.ResponseClienteDto;
import br.com.compass.site.services.CartoesService;
import br.com.compass.site.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;
    private final CartoesService cartoesService;

    @PostMapping
    public ResponseEntity<ResponseClienteDto> criaCliente(@RequestBody @Valid RequestClienteDto requestDto, UriComponentsBuilder uriBuilder) {
        ResponseClienteDto responseDto = clienteService.criaCliente(requestDto);
        URI uri = uriBuilder.path("/api/cliente/{cpf}").buildAndExpand(responseDto.getCpf()).toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ResponseClienteDto>> listaClientes() {
        List<ResponseClienteDto> responseDtoList = clienteService.listaClientes();
        return ResponseEntity.ok(responseDtoList);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ResponseClienteDto> listaUnicoCliente(@PathVariable Long cpf) {
        ResponseClienteDto responseDto = clienteService.listaCliente(cpf);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Void> atualizaCLiente(@PathVariable Long cpf, @RequestBody @Valid RequestPutClienteDto requestDto) {
        clienteService.atualizaCliente(cpf, requestDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cpf}/cartoes")
    public ResponseEntity<ResponseCartoesDto> vinculaCartao(@PathVariable Long cpf, @RequestBody @Valid RequestCartoesDto requestDto,  UriComponentsBuilder uriBuilder) {
        ResponseCartoesDto responseDto = cartoesService.vinculaCartao(cpf, requestDto);
        URI uri = uriBuilder.path("/api/cliente/{cpf}/cartoes/{id}").buildAndExpand(cpf, responseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }


}
