package com.jannesh.dto.cartItem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter @Setter
@ToString
public class SaveCartItemDTO {
    private UUID cartId;
    private UUID itemId;
    private Long quantity;
}
