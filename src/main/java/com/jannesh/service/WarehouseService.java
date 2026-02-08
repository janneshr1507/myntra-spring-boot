package com.jannesh.service;

import com.jannesh.dto.warehouse.SaveWarehouseDTO;
import com.jannesh.dto.warehouse.WarehouseDTO;
import com.jannesh.entity.Warehouse;
import com.jannesh.repository.WarehouseRepository;
import com.jannesh.util.enums.WarehouseStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepo;
    private final ModelMapper modelMapper;

    public Warehouse fetchWarehouseByWarehouseIdAndVendorId(UUID warehouseId, UUID vendorId) {
        if(existsByVendorId(vendorId)) {
            return warehouseRepo.findById(warehouseId)
                    .orElseThrow(() -> new EntityNotFoundException("Warehouse Not Found"));
        }
        throw new RuntimeException("Warehouse is not linked to the vendor");
    }

    public Warehouse fetchWarehouseByWarehouseId(UUID warehouseId) {
        return warehouseRepo.findById(warehouseId)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse Not Found"));
    }

    public WarehouseDTO fetchWarehouseDTOByWarehouseId(UUID warehouseId){
        Warehouse warehouse = fetchWarehouseByWarehouseId(warehouseId);
        return modelMapper.map(warehouse, WarehouseDTO.class);
    }

    public boolean existsByVendorId(UUID vendorId) {
        return warehouseRepo.existsByVendor_VendorId(vendorId);
    }

    public WarehouseDTO createWarehouseDTO(SaveWarehouseDTO requestDTO) {
        if(warehouseRepo.existsByVendor_VendorIdAndPincode(requestDTO.getVendorId(), requestDTO.getPincode()))
            throw new RuntimeException("Warehouse already exists on that pincode");

        Warehouse warehouse = modelMapper.map(requestDTO, Warehouse.class);
        return modelMapper.map(createWarehouse(warehouse), WarehouseDTO.class);
    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouseRepo.save(warehouse);
    }

    public WarehouseDTO modifyWarehouseStatus(UUID warehouseId, WarehouseStatus status) {
        Warehouse warehouse = fetchWarehouseByWarehouseId(warehouseId);
        warehouse.setStatus(status);
        return modelMapper.map(warehouseRepo.save(warehouse), WarehouseDTO.class);
    }
}
