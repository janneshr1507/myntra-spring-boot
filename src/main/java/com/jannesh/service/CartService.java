package com.jannesh.service;

import com.jannesh.dto.cart.AddItemDTO;
import com.jannesh.dto.cartItem.CartItemDTO;
import com.jannesh.entity.*;
import com.jannesh.repository.CartItemRepository;
import com.jannesh.repository.CartRepository;
import com.jannesh.util.enums.CartStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final ItemService itemService;
    private final InventoryService inventoryService;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

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

        inventory.setAvailableQty(inventory.getAvailableQty() - addItemDTO.getQuantity());
        inventory.setReservedQty(inventory.getReservedQty() + addItemDTO.getQuantity());
        inventoryService.createInventory(inventory);

        Cart cart = fetchCartByCustomerId(addItemDTO.getCustomerId());

        CartItem cartItem = null;
        Optional<CartItem> optionalCartItem = cartItemRepo.findByCart_CartId(cart.getCartId());
        if(optionalCartItem.isPresent()) {
            cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + addItemDTO.getQuantity());
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setItem(item);
            cartItem.setQuantity(addItemDTO.getQuantity());
            cartItem.setAmount(item.getSellingPrice());
        }

        CartItemDTO savedCartItemDTO = modelMapper.map(cartItemRepo.save(cartItem), CartItemDTO.class);
        savedCartItemDTO.setCartId(cart.getCartId());
        savedCartItemDTO.setItemId(item.getItemId());
        return savedCartItemDTO;
    }
}
