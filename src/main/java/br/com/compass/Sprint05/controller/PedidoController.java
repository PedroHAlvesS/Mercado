package br.com.compass.Sprint05.controller;

import br.com.compass.Sprint05.dto.pedido.request.RequestPedidoDto;
import br.com.compass.Sprint05.dto.pedido.response.ResponsePedidoDTO;
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
    public ResponseEntity<ResponsePedidoDTO> cadastraPedido(@Valid @RequestBody RequestPedidoDto requestDTO, UriComponentsBuilder uriBuilder) {
        ResponsePedidoDTO responseDTO = pedidoService.salva(requestDTO);
        URI uri = uriBuilder.path("/api/pedidos/{id}").buildAndExpand(responseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }


    @GetMapping
    public ResponseEntity<Page<ResponsePedidoDTO>> listaPedidos(@RequestParam(required = false) String cpf, Pageable pageable) {
        Page<ResponsePedidoDTO> responseDTOList = pedidoService.lista(cpf, pageable);
        return ResponseEntity.ok(responseDTOList);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletaPedido(@PathVariable Long id) {
        pedidoService.deleta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePedidoDTO> detalhaPedido(@PathVariable Long id) {
        ResponsePedidoDTO responseDto = pedidoService.detalha(id);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponsePedidoDTO> atualiza(@PathVariable Long id, @Valid @RequestBody RequestPedidoDto patchDto) {
//        ResponsePedidoDTO responseDto = pedidoService.atualiza(id, patchDto);
//        return ResponseEntity.ok(responseDto);
        return ResponseEntity.ok().build();
    }

}
