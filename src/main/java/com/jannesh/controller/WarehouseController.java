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

    @GetMapping("/getByWarehouse/{warehouseId}")
    public ResponseEntity<?> getWarehouseByWarehouseId(@PathVariable UUID warehouseId) {
        return new ResponseEntity<>(warehouseService.fetchWarehouseDTOByWarehouseId(warehouseId), HttpStatus.OK);
    }

    @GetMapping("/getByVendor/{vendorId}")
    public ResponseEntity<?> getWarehouseListByVendorId(@PathVariable UUID vendorId) {
        return new ResponseEntity<>(warehouseService.fetchWarehouseListByVendorId(vendorId), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveWarehouse(@RequestBody SaveWarehouseDTO requestDTO) {
        return new ResponseEntity<>(warehouseService.createWarehouseDTO(requestDTO), HttpStatus.OK);
    }
}
