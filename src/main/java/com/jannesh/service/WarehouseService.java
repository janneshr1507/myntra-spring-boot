package com.jannesh.service;

import com.jannesh.dto.warehouse.SaveWarehouseDTO;
import com.jannesh.dto.warehouse.WarehouseDTO;
import com.jannesh.entity.Vendor;
import com.jannesh.entity.Warehouse;
import com.jannesh.repository.VendorRepository;
import com.jannesh.repository.WarehouseRepository;
import com.jannesh.util.mapper.WarehouseMapper;
import com.jannesh.util.enums.WarehouseStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepo;
    private final VendorService vendorService;
    private final VendorRepository vendorRepo;
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
        try {
            Vendor vendor = vendorRepo.findById(requestDTO.getVendorId())
                    .orElseThrow(() -> new EntityNotFoundException("Vendor Not Found"));

            Warehouse warehouse = mapper.toEntity(requestDTO);
            warehouse.setVendor(vendor);
            return mapper.toDTO(createWarehouse(warehouse));

        } catch (DataIntegrityViolationException ex) {
            throw handleDataIntegrityViolation(ex);
        }
    }

    private RuntimeException handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Throwable cause = ex.getCause();
        while (cause != null) {
            if (cause instanceof ConstraintViolationException constraintEx) {
                String constraintName = constraintEx.getConstraintName();
                if("SYSTEM.UK_WAREHOUSE_NAME".equalsIgnoreCase(constraintName)) {
                    throw new RuntimeException("Warehouse Already Exists");
                } else if("SYSTEM.UK_WAREHOUSE_CONTACT".equalsIgnoreCase(constraintName)) {
                    throw new RuntimeException("Warehouse Contact Already Exists");
                } else if ("SYSTEM.UK_WAREHOUSE_EMAIL".equalsIgnoreCase(constraintName)) {
                    throw new RuntimeException("Warehouse Email Already Exists");
                } else if("SYSTEM.UK_WAREHOUSE_VENDOR_PINCODE".equalsIgnoreCase(constraintName)) {
                    throw new RuntimeException("Warehouse Vendor-Pincode Already Exists");
                }
            }
            cause = cause.getCause();
        }

        return ex;
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
