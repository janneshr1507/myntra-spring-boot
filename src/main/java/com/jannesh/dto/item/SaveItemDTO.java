package com.jannesh.dto.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
@ToString
public class SaveItemDTO {
    private UUID vendorId;
    private UUID warehouseId;
    private String brand;
    private String model;
    private String color;
    private String size;
    private String fit;
    private String material;
    private BigDecimal actualPrice;
    private BigDecimal discount;
}
