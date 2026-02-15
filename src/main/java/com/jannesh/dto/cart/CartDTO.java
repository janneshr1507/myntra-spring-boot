package com.jannesh.dto.cart;

import com.jannesh.dto.cartItem.CartItemDTO;
import com.jannesh.util.enums.CartStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@ToString
public class CartDTO {
    private UUID cartId;
    private UUID customerId;
    private CartStatus status;
    private List<CartItemDTO> cartItemList;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
