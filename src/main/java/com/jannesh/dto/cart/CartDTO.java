package com.jannesh.dto.cart;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jannesh.dto.cartItem.CartItemDTO;
import com.jannesh.util.enums.CartStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@ToString
@JsonPropertyOrder({"cartId","customerId","totalMRP","totalDiscount","totalAmount","status","cartItemList","createdAt","updatedAt"})
public class CartDTO {
    private UUID cartId;
    private UUID customerId;
    private BigDecimal totalMRP;
    private BigDecimal totalDiscount;
    private BigDecimal totalAmount;
    private CartStatus status;
    private List<CartItemDTO> cartItemList;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
