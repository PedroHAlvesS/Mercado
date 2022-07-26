package br.com.compass.Sprint05.controller;

import br.com.compass.Sprint05.dto.request.PedidoRequestDTO;
import br.com.compass.Sprint05.dto.response.ResponsePedidoDTO;
import br.com.compass.Sprint05.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletaPedido(@PathVariable Long id) {
        pedidoService.deleta(id);
        return ResponseEntity.noContent().build();
    }

}
