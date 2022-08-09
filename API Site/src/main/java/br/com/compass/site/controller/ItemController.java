package br.com.compass.site.controller;


import br.com.compass.site.dto.item.request.RequestItemDto;
import br.com.compass.site.dto.item.response.ResponseItemDto;
import br.com.compass.site.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseItemDto> criaItem(@RequestBody @Valid RequestItemDto requestDto, UriComponentsBuilder uriBuilder) {
        ResponseItemDto responseDto = itemService.criaItem(requestDto);
        URI uri = uriBuilder.path("/api/item/{id}").buildAndExpand(responseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ResponseItemDto>> listaItens() {
        List<ResponseItemDto> responseItemDtoList = itemService.listaItens();
        return ResponseEntity.ok(responseItemDtoList);
    }

}
