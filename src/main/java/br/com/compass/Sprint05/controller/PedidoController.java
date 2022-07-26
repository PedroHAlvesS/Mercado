package br.com.compass.Sprint05.controller;

import br.com.compass.Sprint05.dto.request.PedidoRequestDTO;
import br.com.compass.Sprint05.dto.response.ResponsePedidoDTO;
import br.com.compass.Sprint05.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @Transactional
    public ResponseEntity<ResponsePedidoDTO> cadastraPedido(@RequestBody @Valid PedidoRequestDTO requestDTO, UriComponentsBuilder uriBuilder) {
        ResponsePedidoDTO responseDTO = pedidoService.salva(requestDTO);
        URI uri = uriBuilder.path("/api/pedidos/{id}").buildAndExpand(responseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ResponsePedidoDTO>> listaPedidos(@RequestParam(required = false) String cpf, Pageable pageable) {
        Page<ResponsePedidoDTO> responseDTOList = pedidoService.lista(cpf, pageable);
        return ResponseEntity.ok(responseDTOList);
    }
}
