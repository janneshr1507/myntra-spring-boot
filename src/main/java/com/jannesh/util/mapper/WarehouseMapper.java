package com.jannesh.util.mapper;

import com.jannesh.dto.warehouse.SaveWarehouseDTO;
import com.jannesh.dto.warehouse.WarehouseDTO;
import com.jannesh.entity.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    @Mapping(source = "vendor.vendorId", target = "vendorId")
    WarehouseDTO toDTO(Warehouse warehouse);

    @Mapping(source = "vendorId", target = "vendor.vendorId")
    Warehouse toEntity(WarehouseDTO warehouseDTO);

    Warehouse toEntity(SaveWarehouseDTO warehouseDTO);
}
