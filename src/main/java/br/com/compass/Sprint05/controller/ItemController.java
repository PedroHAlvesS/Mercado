package br.com.compass.Sprint05.controller;

import br.com.compass.Sprint05.dto.item.request.RequestAtualizaItemDto;
import br.com.compass.Sprint05.dto.item.request.RequestItemDto;
import br.com.compass.Sprint05.dto.item.response.ResponseItemDto;
import br.com.compass.Sprint05.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseItemDto> atualiza(@PathVariable Long id, @RequestBody RequestAtualizaItemDto patchDto) {
        ResponseItemDto responseDto = itemService.atualiza(id, patchDto);
        return ResponseEntity.ok(responseDto);

    }

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseItemDto> criaItem(@Valid @RequestBody RequestItemDto requestItemDto, UriComponentsBuilder uriBuilder) {
        ResponseItemDto responseDto = itemService.cria(requestItemDto);
        URI uri = uriBuilder.path("/api/item/{id}").buildAndExpand(responseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }
}
