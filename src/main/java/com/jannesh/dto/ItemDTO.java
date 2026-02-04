package com.jannesh.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @Setter
@ToString
public class ItemDTO {
    private Long itemId;
    private String brand;
    private String model;
    private BigDecimal price;
    private String color;
    private String size;
    private String fit;
    private String material;
}
