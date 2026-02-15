package com.jannesh.util.mapper;

import com.jannesh.dto.cart.CartDTO;
import com.jannesh.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "customer.customerId", target = "customerId")
    CartDTO toDTO(Cart cart);
}
