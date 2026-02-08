package com.jannesh.dto.inventory;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter @Setter
@ToString
public class SaveInventoryDTO {
    private UUID itemId;
    private Long availableQty;
}
