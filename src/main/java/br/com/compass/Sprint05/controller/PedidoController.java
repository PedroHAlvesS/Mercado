package br.com.compass.Sprint05.controller;

import br.com.compass.Sprint05.dto.request.PedidoRequestDTO;
import br.com.compass.Sprint05.dto.response.ResponsePedidoDTO;
import br.com.compass.Sprint05.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @Transactional
    public ResponseEntity<ResponsePedidoDTO> cadastraPedido(@RequestBody @Valid PedidoRequestDTO requestDTO) {
        ResponsePedidoDTO responseDTO = pedidoService.salva(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
