package com.jannesh.service;

import com.jannesh.dto.vendor.SaveVendorDTO;
import com.jannesh.dto.vendor.VendorDTO;
import com.jannesh.entity.Vendor;
import com.jannesh.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VendorService {
    private final VendorRepository vendorRepo;
    private final ModelMapper modelMapper;

    public Vendor fetchVendorByVendorId(UUID inventoryId) {
        return vendorRepo.findById(inventoryId)
                .orElseThrow(() -> new EntityNotFoundException("Vendor Not Found"));
    }

    public VendorDTO createVendorDTO(SaveVendorDTO requestDTO) {
        Vendor vendor = modelMapper.map(requestDTO, Vendor.class);
        return modelMapper.map(createVendor(vendor), VendorDTO.class);
    }

    public Vendor createVendor(Vendor vendor) {
        try {
            return vendorRepo.save(vendor);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Vendor with that name already exists");
        }
    }
}
