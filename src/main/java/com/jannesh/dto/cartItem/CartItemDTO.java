package com.jannesh.dto.cartItem;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
public class CartItemDTO {
    private UUID cartItemId;
    private UUID cartId;
    private UUID itemId;
    private Long quantity;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
