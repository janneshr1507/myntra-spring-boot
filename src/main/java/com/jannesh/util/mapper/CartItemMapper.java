package com.jannesh.util.mapper;

import com.jannesh.dto.cartItem.CartItemDTO;
import com.jannesh.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(source = "cart.cartId", target = "cartId")
    @Mapping(source = "item.itemId", target = "itemId")
    CartItemDTO toDTO(CartItem cartItem);

    List<CartItemDTO> toDTO(List<CartItem> cartItems);
}
