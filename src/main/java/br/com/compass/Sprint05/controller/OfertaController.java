package br.com.compass.Sprint05.controller;

import br.com.compass.Sprint05.dto.item.response.ResponseItemDto;
import br.com.compass.Sprint05.dto.oferta.request.RequestOfertaDto;
import br.com.compass.Sprint05.dto.oferta.response.ResponseOfertaDto;
import br.com.compass.Sprint05.service.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/oferta")
public class OfertaController {
    @Autowired
    private OfertaService ofertaService;

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseOfertaDto> criaItem(@Valid @RequestBody RequestOfertaDto requestDto, UriComponentsBuilder uriBuilder) {
        ResponseOfertaDto responseDto = ofertaService.cria(requestDto);
        URI uri = uriBuilder.path("/api/item/{id}").buildAndExpand(responseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }
}
