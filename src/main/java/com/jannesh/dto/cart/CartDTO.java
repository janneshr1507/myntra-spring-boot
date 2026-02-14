package com.jannesh.dto.cart;

import com.jannesh.util.enums.CartStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@ToString
public class CartDTO {
    private UUID cartId;
    private UUID customerId;
    private CartStatus status;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
