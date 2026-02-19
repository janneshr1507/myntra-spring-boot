package com.jannesh.util.mapper;

import com.jannesh.dto.item.ItemDTO;
import com.jannesh.dto.item.SaveItemDTO;
import com.jannesh.dto.item.UpdateItemDTO;
import com.jannesh.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(source = "vendor.vendorId", target = "vendorId")
    @Mapping(source = "warehouse.warehouseId", target = "warehouseId")
    ItemDTO toDTO(Item item);

    @Mapping(source = "vendorId", target = "vendor.vendorId")
    @Mapping(source = "warehouseId", target = "warehouse.warehouseId")
    Item toEntity(ItemDTO itemDTO);

    Item toEntity(SaveItemDTO requestDTO);

    void toEntity(UpdateItemDTO requestDTO,@MappingTarget Item item);
}
