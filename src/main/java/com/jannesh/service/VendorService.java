package com.jannesh.service;

import com.jannesh.dto.vendor.SaveVendorDTO;
import com.jannesh.dto.vendor.VendorDTO;
import com.jannesh.entity.Vendor;
import com.jannesh.repository.VendorRepository;
import com.jannesh.util.mapper.VendorMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendorService {
    private final VendorRepository vendorRepo;
    private final VendorMapper mapper;

    @Transactional
    public VendorDTO fetchVendorDetails(UUID vendorId) {
        Vendor vendor = vendorRepo.findById(vendorId)
                .orElseThrow(()->new EntityNotFoundException("Vendor Not Found"));
        return mapper.toDTO(vendor);
    }

    public Vendor fetchVendorByVendorId(UUID inventoryId) {
        return vendorRepo.findById(inventoryId)
                .orElseThrow(() -> new EntityNotFoundException("Vendor Not Found"));
    }

    public VendorDTO createVendorDTO(SaveVendorDTO requestDTO) {
        try {
            Vendor vendor = mapper.toEntity(requestDTO);
            return mapper.toDTO(vendorRepo.save(vendor));
        } catch (DataIntegrityViolationException ex) {
            throw handleDataIntegrityViolation(ex);
        }
    }

    private RuntimeException handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Throwable cause = ex.getCause();
        while (cause != null) {
            if (cause instanceof ConstraintViolationException constraintEx) {
                String constraintName = constraintEx.getConstraintName();
                if("SYSTEM.UK_VENDOR_NAME".equalsIgnoreCase(constraintName)) {
                    throw new RuntimeException("Vendor Already Exists");
                }
            }
            cause = cause.getCause();
        }

        return ex;
    }

}
