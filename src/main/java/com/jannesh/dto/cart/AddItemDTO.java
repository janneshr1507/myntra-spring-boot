package com.jannesh.dto.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter @Setter
@ToString
public class AddItemDTO {
    private UUID customerId;
    private UUID itemId;
    private Long quantity;
}
