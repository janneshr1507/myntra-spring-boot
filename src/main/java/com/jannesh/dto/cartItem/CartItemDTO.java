package com.jannesh.dto.cartItem;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@ToString
@JsonPropertyOrder({"cartItemId","cartId","itemId","quantity","amount","createdAt","updatedAt"})
public class CartItemDTO {
    private UUID cartItemId;
    private UUID cartId;
    private UUID itemId;
    private Long quantity;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
