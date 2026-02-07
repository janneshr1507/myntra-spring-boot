package com.jannesh.controller;

import com.jannesh.dto.warehouse.SaveWarehouseDTO;
import com.jannesh.dto.warehouse.WarehouseDTO;
import com.jannesh.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PostMapping("/save")
    public WarehouseDTO saveWarehouse(@RequestBody SaveWarehouseDTO requestDTO) {
        return warehouseService.createWarehouseDTO(requestDTO);
    }
}
