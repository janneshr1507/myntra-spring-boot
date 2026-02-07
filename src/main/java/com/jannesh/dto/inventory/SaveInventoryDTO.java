package com.jannesh.dto.inventory;

import com.jannesh.dto.item.SaveItemDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter @Setter
@ToString
public class SaveInventoryDTO {
    private UUID vendorId;
    private UUID warehouseId;
    private SaveItemDTO item;
    private Long availableQty;
}
