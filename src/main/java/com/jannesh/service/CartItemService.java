package com.jannesh.service;

import com.jannesh.dto.cartItem.CartItemDTO;
import com.jannesh.entity.CartItem;
import com.jannesh.repository.CartItemRepository;
import com.jannesh.util.mapper.CartItemMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepo;
    private final CartItemMapper mapper;

    public CartItem createCartItem(CartItem cartItem) {
        return cartItemRepo.save(cartItem);
    }

    public CartItem fetchCartItem(UUID cartId, UUID itemId) {
        return cartItemRepo.findByCart_CartIdAndItem_ItemIdWithItem(cartId, itemId)
                .orElse(new CartItem());
    }

    public List<CartItem> fetchCartItem(UUID cartId) {
        return cartItemRepo.findByCart_CartIdWithItem(cartId);
    }

    public List<CartItemDTO> fetchCartItemDTO(UUID cartId) {
        List<CartItem> cartItemList = fetchCartItem(cartId);

        List<CartItemDTO> cartItemDTOList = new ArrayList<>();
        for(CartItem cartItem: cartItemList) {
            CartItemDTO cartItemDTO = mapper.toDTO(cartItem);
            cartItemDTOList.add(cartItemDTO);
        }

        return cartItemDTOList;
    }

    public void deleteCartItem(UUID cartItemId) {
        cartItemRepo.deleteById(cartItemId);
    }

    public CartItem fetchCartItemByCartItemId(UUID cartItemId) {
        return cartItemRepo.findByIdWithItem(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartItem Not Found"));
    }
}
