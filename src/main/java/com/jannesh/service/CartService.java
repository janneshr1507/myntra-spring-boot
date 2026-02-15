package com.jannesh.service;

import com.jannesh.dto.cart.AddItemDTO;
import com.jannesh.dto.cartItem.CartItemDTO;
import com.jannesh.entity.*;
import com.jannesh.repository.CartItemRepository;
import com.jannesh.repository.CartRepository;
import com.jannesh.util.enums.CartStatus;
import com.jannesh.util.mapper.CartItemMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final ItemService itemService;
    private final InventoryService inventoryService;
    private final CustomerService customerService;
    private final CartItemMapper cartItemMapper;

    public Cart createCart(UUID customerId) {
        boolean status = cartRepo.existsByCustomer_CustomerIdAndStatus(customerId, CartStatus.OPEN);
        if(status)
            throw new RuntimeException("Already cart is open on the given customerId");

        Cart cart = new Cart();
        Customer customer = customerService.fetchCustomerByCustomerId(customerId);
        cart.setCustomer(customer);
        return cartRepo.save(cart);
    }

    public Cart fetchCartByCustomerId(UUID customerId) {
        return cartRepo.findByCustomer_CustomerIdAndStatus(customerId, CartStatus.OPEN)
                .orElseGet(() -> createCart(customerId));
    }

    public CartItemDTO addItemToCart(AddItemDTO addItemDTO) {
        Customer customer = customerService.fetchCustomerByCustomerId(addItemDTO.getCustomerId());
        Item item = itemService.fetchItemByItemId(addItemDTO.getItemId());

        Inventory inventory = inventoryService.fetchInventoryByItemId(addItemDTO.getItemId());
        if(addItemDTO.getQuantity() > inventory.getAvailableQty()) {
            throw new RuntimeException("Stock Not Available");
        }

        Cart cart = fetchCartByCustomerId(addItemDTO.getCustomerId());
        CartItem cartItem = cartItemRepo.findByCart_CartIdAndItem_ItemId(cart.getCartId(), item.getItemId())
                .orElse(new CartItem());

        if(cartItem.getCartItemId() == null) {
            cartItem.setCart(cart);
            cartItem.setItem(item);
        }

        inventory.setAvailableQty(inventory.getAvailableQty() - addItemDTO.getQuantity());
        inventory.setReservedQty(inventory.getReservedQty() + addItemDTO.getQuantity());
        inventoryService.createInventory(inventory);

        cartItem.setQuantity((cartItem.getQuantity() == null ? 0L : cartItem.getQuantity()) + addItemDTO.getQuantity());
        cartItem.setAmount(item.getSellingPrice());

        return cartItemMapper.toDTO(cartItemRepo.save(cartItem));
    }

    public void removeCartItem(UUID cartId, UUID itemId) {
        CartItem cartItem = cartItemRepo.findByCart_CartIdAndItem_ItemId(cartId, itemId)
                .orElseThrow(() -> new EntityNotFoundException("Cart Item not found"));

        Inventory inventory = inventoryService.fetchInventoryByItemId(itemId);
        inventory.setAvailableQty(inventory.getAvailableQty() + cartItem.getQuantity());
        inventory.setReservedQty(inventory.getReservedQty() - cartItem.getQuantity());
        inventoryService.createInventory(inventory);

        cartItemRepo.delete(cartItem);
    }
}
