package com.jannesh.dto.item;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
@ToString
@JsonPropertyOrder({"itemId","brand","model","price","color","size","fit","material","actualPrice","discount","sellingPrice","averageRatings","totalNumberOfRatings"})
public class ItemDTO {
    private UUID itemId;
    private String brand;
    private String model;
    private String color;
    private String size;
    private String fit;
    private String material;
    private BigDecimal actualPrice;
    private BigDecimal discount;
    private BigDecimal sellingPrice;
    private BigDecimal averageRatings;
    private Long totalNumberOfRatings;
}
