package com.jannesh.controller;

import com.jannesh.dto.inventory.InventoryDTO;
import com.jannesh.dto.inventory.SaveInventoryDTO;
import com.jannesh.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("/save")
    public InventoryDTO saveInventory(@RequestBody SaveInventoryDTO requestDTO) {
        return inventoryService.createInventoryDTO(requestDTO);
    }

    @GetMapping("/get/{itemId}")
    public ResponseEntity<?> getInventoryDTOByItemId(@PathVariable("itemId") UUID itemId) {
        return new ResponseEntity<>(inventoryService.fetchInventoryDTOByItemId(itemId), HttpStatus.OK);
    }
}
