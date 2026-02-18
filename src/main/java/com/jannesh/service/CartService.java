package com.jannesh.service;

import com.jannesh.dto.cart.AddItemDTO;
import com.jannesh.dto.cart.CartDTO;
import com.jannesh.dto.cartItem.CartItemDTO;
import com.jannesh.entity.*;
import com.jannesh.repository.CartRepository;
import com.jannesh.util.enums.CartStatus;
import com.jannesh.util.mapper.CartMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepo;
    private final ItemService itemService;
    private final InventoryService inventoryService;
    private final CustomerService customerService;
    private final CartItemService cartItemService;
    private final CartMapper mapper;

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
        boolean customerStatus = customerService.existsByCustomerId(addItemDTO.getCustomerId());
        if(!customerStatus) throw new EntityNotFoundException("Customer Not Found");

        Item item = itemService.fetchItemByItemId(addItemDTO.getItemId());

        Inventory inventory = inventoryService.fetchInventoryByItemId(addItemDTO.getItemId());
        if(addItemDTO.getQuantity() > inventory.getAvailableQty()) {
            throw new RuntimeException("Stock Not Available");
        }

        Cart cart = fetchCartByCustomerId(addItemDTO.getCustomerId());
        CartItem cartItem = new CartItem();

        cartItem.setCart(cart);
        cartItem.setItem(item);

        inventory.setAvailableQty(inventory.getAvailableQty() - addItemDTO.getQuantity());
        inventory.setReservedQty(inventory.getReservedQty() + addItemDTO.getQuantity());
        inventoryService.createInventory(inventory);

        cartItem.setQuantity(addItemDTO.getQuantity());
        cartItem.setAmount(item.getSellingPrice());

        return cartItemService.createCartItemDTO(cartItem);
    }

    public void removeItemFromCart(UUID cartId, UUID itemId) {
        CartItem cartItem = cartItemService.fetchCartItem(cartId, itemId);

        Inventory inventory = inventoryService.fetchInventoryByItemId(itemId);
        inventory.setAvailableQty(inventory.getAvailableQty() + cartItem.getQuantity());
        inventory.setReservedQty(inventory.getReservedQty() - cartItem.getQuantity());
        inventoryService.createInventory(inventory);

        cartItemService.deleteCartItem(cartItem.getCartItemId());
    }

    public List<CartDTO> fetchCartList() {
        List<Cart> cartList = cartRepo.findAll();

        List<CartDTO> cartDTOList = new ArrayList<>();
        for(Cart cart: cartList) {
            CartDTO cartDTO = mapper.toDTO(calculateCartAmount(cart));
            cartDTO.setCartItemList(cartItemService.fetchCartItemDTO(cart.getCartId()));
            cartDTOList.add(cartDTO);
        }

        return cartDTOList;
    }

    public Cart fetchCartByCartId(UUID cartId) {
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart Not Found"));
        return calculateCartAmount(cart);
    }

    public CartDTO fetchCartDTOByCartId(UUID cartId) {
        Cart cart = fetchCartByCartId(cartId);
        CartDTO cartDTO = mapper.toDTO(cart);

        List<CartItemDTO> cartItemDTOList = cartItemService.fetchCartItemDTO(cartId);
        cartDTO.setCartItemList(cartItemDTOList);

        return cartDTO;
    }

    public Cart calculateCartAmount(Cart cart) {

        cart.setTotalDiscount(BigDecimal.ZERO);
        cart.setTotalMRP(BigDecimal.ZERO);
        cart.setTotalAmount(BigDecimal.ZERO);

        List<CartItem> cartItemList = cartItemService.fetchCartItem(cart.getCartId());
        for(CartItem cartItem: cartItemList) {
            Item item = cartItem.getItem();
            BigDecimal quantity = BigDecimal.valueOf(cartItem.getQuantity());

            BigDecimal itemMRP = item.getActualPrice().multiply(quantity);
            BigDecimal itemDiscount = item.getActualPrice()
                            .multiply(item.getDiscount())
                            .multiply(quantity);
            BigDecimal itemSelling = item.getSellingPrice().multiply(quantity);

            cart.setTotalMRP(cart.getTotalMRP().add(itemMRP));
            cart.setTotalDiscount(cart.getTotalDiscount().add(itemDiscount));
            cart.setTotalAmount(cart.getTotalAmount().add(itemSelling));
        }
        return cartRepo.save(cart);
    }
}
