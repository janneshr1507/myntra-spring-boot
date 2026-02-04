package com.jannesh.controller;

import com.jannesh.dto.ItemDTO;
import com.jannesh.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getItemById(@PathVariable("itemId") Long itemId) {
        ItemDTO itemDTO = itemService.fetchItemDetailsByItemId(itemId);
        return new ResponseEntity<>(itemDTO, HttpStatus.OK);
    }

}
