package com.jannesh.util.mapper;

import com.jannesh.dto.inventory.InventoryDTO;
import com.jannesh.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(source = "item.itemId", target = "itemId")
    InventoryDTO toDTO (Inventory inventory);
}
