package com.jannesh.dto.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
@ToString
public class ItemDTO {
    private UUID itemId;
    private String brand;
    private String model;
    private BigDecimal price;
    private String color;
    private String size;
    private String fit;
    private String material;
}
