package com.jannesh.controller;

import com.jannesh.dto.item.ItemDTO;
import com.jannesh.dto.item.SaveItemDTO;
import com.jannesh.dto.item.UpdateItemDTO;
import com.jannesh.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getItemById(@PathVariable("itemId") UUID itemId) {
        ItemDTO itemDTO = itemService.fetchItemDTOByItemId(itemId);
        return new ResponseEntity<>(itemDTO, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveItem(@RequestBody SaveItemDTO requestDTO) {
        ItemDTO itemDTO = itemService.createItemDTO(requestDTO);
        return new ResponseEntity<>(itemDTO, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateItem(@RequestBody UpdateItemDTO requestDTO) {
        return new ResponseEntity<>(itemService.updateItemDetails(requestDTO), HttpStatus.OK);
    }

}
