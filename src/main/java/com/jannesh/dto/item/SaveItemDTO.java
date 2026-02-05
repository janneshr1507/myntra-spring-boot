package com.jannesh.dto.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @Setter
@ToString
public class SaveItemDTO {
    private String brand;
    private String model;
    private BigDecimal price;
    private String color;
    private String size;
    private String fit;
    private String material;
}
