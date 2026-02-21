package com.jannesh.service;

import com.jannesh.dto.cart.AddItemDTO;
import com.jannesh.dto.cart.CartDTO;
import com.jannesh.dto.cartItem.CartItemDTO;
import com.jannesh.entity.*;
import com.jannesh.repository.CartRepository;
import com.jannesh.util.enums.CartStatus;
import com.jannesh.util.mapper.CartMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Transactional
    public Cart createCart(UUID customerId) {
        Cart cart = new Cart();
        Customer customer = customerService.fetchCustomerByCustomerId(customerId);
        cart.setCustomer(customer);
        return cartRepo.save(cart);
    }

    public Cart fetchCartByCustomerId(UUID customerId) {
        return cartRepo.findByCustomer_CustomerIdAndCartStatus(customerId, CartStatus.OPEN)
                .orElseGet(() -> createCart((customerId)));
    }

    @Transactional
    public CartDTO addItemToCart(AddItemDTO addItemDTO) {
        //Check Customer Status
        boolean customerStatus = customerService.existsByCustomerId(addItemDTO.getCustomerId());
        if(!customerStatus) throw new EntityNotFoundException("Customer Not Found");

        //Check Item Status
        Item item = itemService.fetchItemByItemId(addItemDTO.getItemId());

        //Check Inventory Status & Reserve Stock if available
        inventoryService.reserveInventoryForCart(addItemDTO.getItemId(), addItemDTO.getQuantity());

        //Fetch existing open cart for the customer or create new
        Cart cart = fetchCartByCustomerId(addItemDTO.getCustomerId());

        //Prepare Item (If Item already present in the Cart then update quantity)
        CartItem cartItem = cartItemService.fetchCartItem(cart.getCartId(), addItemDTO.getItemId());
        if(cartItem.getCartItemId() == null) {
            cartItem.setCart(cart);
            cartItem.setItem(item);
            cartItem.setQuantity(addItemDTO.getQuantity());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + addItemDTO.getQuantity());
        }

        //Calculating amount by multiplying sellingPrice and quantity.
        cartItem.setAmount(item.getSellingPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));

        //Add Item to the Cart
        cartItemService.createCartItem(cartItem);

        //Calculate Cart Amount !!!NEED TO FIX ==> LOADING ITEM TO AVOID LAZY LOADING ISSUE!!!
        calculateCartAmountWhenItemAdded(cart, cartItemService.fetchCartItemByCartItemId(cartItem.getCartItemId()));

        //Save Cart in the DB
        Cart updatedCart = cartRepo.save(cart);

        //Prepare for Response
        CartDTO cartDTO = mapper.toDTO(updatedCart);
        List<CartItemDTO> cartItemDTOList = cartItemService.fetchCartItemDTO(updatedCart.getCartId());
        cartDTO.setCartItemList(cartItemDTOList);
        return cartDTO;
    }

    public CartDTO removeItemFromCart(UUID cartItemId) {
        CartItem cartItem = cartItemService.fetchCartItemByCartItemId(cartItemId);
        cartItemService.deleteCartItem(cartItem.getCartItemId());

        calculateCartAmountWhenItemRemove(cartItem.getCart(), cartItem);
        cartRepo.save(cartItem.getCart());

        inventoryService.releaseInventoryFromCart(cartItem.getItem().getItemId(), cartItem.getQuantity());

        return fetchCartDTOByCartId(cartItem.getCart().getCartId());
    }

    public Cart fetchCartByCartId(UUID cartId) {
        return cartRepo.findByCartId(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart Not Found"));
    }

    @Transactional
    public CartDTO fetchCartDTOByCartId(UUID cartId) {
        Cart cart = fetchCartByCartId(cartId);
        return mapper.toDTO(cart);
    }

    public void calculateCartAmountWhenItemAdded(Cart cart, CartItem cartItem) {
        BigDecimal totalMRP = cart.getTotalMRP().add(cartItem.getItem().getActualPrice());
        BigDecimal totalDiscount = cart.getTotalDiscount().add(cartItem.getItem().getActualPrice().subtract(cartItem.getItem().getSellingPrice()));
        BigDecimal totalAmount = cart.getTotalAmount().add(cartItem.getItem().getSellingPrice());

        cart.setTotalMRP(totalMRP);
        cart.setTotalDiscount(totalDiscount);
        cart.setTotalAmount(totalAmount);
    }

    public void calculateCartAmountWhenItemRemove(Cart cart, CartItem cartItem) {
        BigDecimal totalMRP = cart.getTotalMRP().subtract(cartItem.getItem().getActualPrice());
        BigDecimal totalDiscount = cart.getTotalDiscount().subtract(cartItem.getItem().getActualPrice().subtract(cartItem.getItem().getSellingPrice()));
        BigDecimal totalAmount = cart.getTotalAmount().subtract(cartItem.getItem().getSellingPrice());

        cart.setTotalMRP(totalMRP);
        cart.setTotalDiscount(totalDiscount);
        cart.setTotalAmount(totalAmount);
    }
}
