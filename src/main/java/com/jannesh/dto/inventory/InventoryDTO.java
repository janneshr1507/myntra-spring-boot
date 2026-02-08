package com.jannesh.dto.inventory;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@ToString
@JsonPropertyOrder({"inventoryId","itemId","availableQty","reservedQty","soldQty","createdAt","updatedAt"})
public class InventoryDTO {
    private UUID inventoryId;
    private UUID itemId;

    private Long availableQty;
    private Long reservedQty;
    private Long soldQty;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
