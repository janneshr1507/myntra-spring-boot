package com.jannesh.controller;

import com.jannesh.dto.warehouse.SaveWarehouseDTO;
import com.jannesh.service.WarehouseService;
import com.jannesh.util.enums.WarehouseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PostMapping("/save")
    public ResponseEntity<?> saveWarehouse(@RequestBody SaveWarehouseDTO requestDTO) {
        return new ResponseEntity<>(warehouseService.createWarehouseDTO(requestDTO), HttpStatus.OK);
    }

    @GetMapping("/modify/warehouseStatus/{warehouseId}/{status}")
    public ResponseEntity<?> changeWarehouseStatus(@PathVariable("warehouseId") UUID warehouseId, @PathVariable("status") WarehouseStatus status) {
        return new ResponseEntity<>(warehouseService.modifyWarehouseStatus(warehouseId, status), HttpStatus.OK);
    }
}
