package br.com.compass.site.controller;

import br.com.compass.site.dto.cliente.request.RequestClienteDto;
import br.com.compass.site.dto.cliente.request.RequestPutClienteDto;
import br.com.compass.site.dto.cliente.response.ResponseClienteDto;
import br.com.compass.site.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    @PostMapping
    public ResponseEntity<ResponseClienteDto> criaCliente(@RequestBody @Valid RequestClienteDto requestDto, UriComponentsBuilder uriBuilder) {
        ResponseClienteDto responseDto = clienteService.criaCliente(requestDto);
        URI uri = uriBuilder.path("/api/cliente/{id}").buildAndExpand(responseDto.getCpf()).toUri();
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

}
