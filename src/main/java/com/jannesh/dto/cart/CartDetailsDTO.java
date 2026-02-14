package com.jannesh.dto.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter @Setter
@ToString
public class CartDetailsDTO {
    private UUID cartId;
    private UUID customerId;
}
