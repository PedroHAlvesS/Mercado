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
    public ResponseEntity<ResponseClienteDto> criaCliente(@Valid @RequestBody RequestClienteDto requestDto, UriComponentsBuilder uriBuilder) {
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
    public ResponseEntity<ResponseClienteDto> listaUnicoCliente(@PathVariable String cpf) {
        ResponseClienteDto responseDto = clienteService.listaCliente(cpf);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Void> atualizaCLiente(@PathVariable String cpf, @Valid @RequestBody RequestPutClienteDto requestDto) {
        clienteService.atualizaCliente(cpf, requestDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cpf}/cartoes")
    public ResponseEntity<ResponseCartoesDto> vinculaCartao(@PathVariable String cpf, @Valid @RequestBody RequestCartoesDto requestDto,  UriComponentsBuilder uriBuilder) {
        ResponseCartoesDto responseDto = cartoesService.vinculaCartao(cpf, requestDto);
        URI uri = uriBuilder.path("/api/cliente/{cpf}/cartoes/{id}").buildAndExpand(cpf, responseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }

    @GetMapping("/{cpf}/cartoes")
    public ResponseEntity<List<ResponseCartoesDto>> mostraCartoesDoCliente(@PathVariable String cpf) {
        List<ResponseCartoesDto> responseDtoList = cartoesService.mostraCartoesDoCliente(cpf);
        return ResponseEntity.ok(responseDtoList);
    }

    @GetMapping("/{cpf}/cartoes/{id}")
    public ResponseEntity<ResponseCartoesDto> mostraCartaoUnitario(@PathVariable String cpf, @PathVariable Long id) {
        ResponseCartoesDto responseDto = cartoesService.mostraCartaoDoClienteUnitario(cpf, id);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{cpf}/cartoes/{id}")
    public ResponseEntity<Void> atualizaCartao(@PathVariable String cpf, @PathVariable Long id, @Valid @RequestBody RequestCartoesDto requestDto) {
        cartoesService.atualizaCartao(cpf, id, requestDto);
        return ResponseEntity.noContent().build();
    }

}
