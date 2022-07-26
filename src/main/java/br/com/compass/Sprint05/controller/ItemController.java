package br.com.compass.Sprint05.controller;

import br.com.compass.Sprint05.dto.item.RequestAtualizaItemDto;
import br.com.compass.Sprint05.dto.item.ResponseItemDto;
import br.com.compass.Sprint05.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
}
