package com.jannesh.controller;

import com.jannesh.dto.inventory.InventoryDTO;
import com.jannesh.dto.inventory.SaveInventoryDTO;
import com.jannesh.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("/save")
    public InventoryDTO saveInventory(@RequestBody SaveInventoryDTO requestDTO) {
        return inventoryService.createInventoryDTO(requestDTO);
    }
}
