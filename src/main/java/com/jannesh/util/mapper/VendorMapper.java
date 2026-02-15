package com.jannesh.util.mapper;

import com.jannesh.dto.vendor.SaveVendorDTO;
import com.jannesh.dto.vendor.VendorDTO;
import com.jannesh.entity.Vendor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VendorMapper {
    VendorDTO toDTO(Vendor vendor);
    Vendor toEntity(SaveVendorDTO vendorDTO);
}
