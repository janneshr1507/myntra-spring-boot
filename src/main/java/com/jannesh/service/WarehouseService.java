package com.jannesh.service;

import com.jannesh.dto.warehouse.SaveWarehouseDTO;
import com.jannesh.dto.warehouse.WarehouseDTO;
import com.jannesh.entity.Vendor;
import com.jannesh.entity.Warehouse;
import com.jannesh.repository.WarehouseRepository;
import com.jannesh.util.mapper.WarehouseMapper;
import com.jannesh.util.enums.WarehouseStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepo;
    private final VendorService vendorService;
    private final WarehouseMapper mapper;

    public Warehouse fetchWarehouseByWarehouseIdAndVendorId(UUID warehouseId, UUID vendorId) {
        return warehouseRepo.findByWarehouseIdAndVendor_VendorId(warehouseId, vendorId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
    }

    public Warehouse fetchWarehouseByWarehouseId(UUID warehouseId) {
        return warehouseRepo.findById(warehouseId)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse Not Found"));
    }

    public WarehouseDTO fetchWarehouseDTOByWarehouseId(UUID warehouseId){
        Warehouse warehouse = fetchWarehouseByWarehouseId(warehouseId);
        return mapper.toDTO(warehouse);
    }

    public WarehouseDTO createWarehouseDTO(SaveWarehouseDTO requestDTO) {
        if(warehouseRepo.existsByVendor_VendorIdAndPincode(requestDTO.getVendorId(), requestDTO.getPincode()))
            throw new RuntimeException("Warehouse already exists on that pincode");

        Vendor vendor = vendorService.fetchVendorByVendorId(requestDTO.getVendorId());
        Warehouse warehouse = mapper.toEntity(requestDTO);
        warehouse.setVendor(vendor);

        return mapper.toDTO(createWarehouse(warehouse));
    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouseRepo.save(warehouse);
    }

    public WarehouseDTO modifyWarehouseStatus(UUID warehouseId, WarehouseStatus status) {
        Warehouse warehouse = fetchWarehouseByWarehouseId(warehouseId);
        warehouse.setStatus(status);
        return mapper.toDTO(warehouse);
    }

    public List<WarehouseDTO> fetchWarehouseListByVendorId(UUID vendorId) {
        List<Warehouse> warehouseList = warehouseRepo.findByVendor_VendorId(vendorId);
        if(warehouseList.isEmpty()) throw new RuntimeException("No warehouse available for the vendorId");

        List<WarehouseDTO> warehouseDTOList = new ArrayList<>();
        for(Warehouse warehouse: warehouseList) {
            WarehouseDTO warehouseDTO = mapper.toDTO(warehouse);
            warehouseDTOList.add(warehouseDTO);
        }

        return warehouseDTOList;
    }
}
