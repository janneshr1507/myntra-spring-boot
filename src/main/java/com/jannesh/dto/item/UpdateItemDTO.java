package com.jannesh.dto.item;

import com.jannesh.util.enums.ItemStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
@ToString
public class UpdateItemDTO {
    private UUID itemId;
    private String brand;
    private String model;
    private String color;
    private String size;
    private String fit;
    private String material;
    private BigDecimal actualPrice;
    private BigDecimal discount;
    private ItemStatus itemStatus;
}
