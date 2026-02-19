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
import org.hibernate.Hibernate;
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
        cartItem.setCart(cart);
        cartItem.setItem(item);
        if(cartItem.getCartItemId() == null) {
            cartItem.setQuantity(addItemDTO.getQuantity());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + addItemDTO.getQuantity());
        }

        //Calculating amount by multiplying sellingPrice and quantity.
        cartItem.setAmount(item.getSellingPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));

        //Add Item to the Cart
        cartItemService.createCartItem(cartItem);

        //Calculate Cart Amount !!!NEED TO FIX ==> LOADING ITEM TO AVOID LAZY LOADING ISSUE!!!
        calculateCartAmount(cart, cartItemService.fetchCartItemByCartItemId(cartItem.getCartItemId()));

        //Save Cart in the DB
        Cart updatedCart = cartRepo.save(cart);

        //Prepare for Response
        CartDTO cartDTO = mapper.toDTO(cart);
        List<CartItemDTO> cartItemDTOList = cartItemService.fetchCartItemDTO(updatedCart.getCartId());
        cartDTO.setCartItemList(cartItemDTOList);
        return cartDTO;
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

    public void calculateCartAmount(Cart cart, CartItem cartItem) {

        BigDecimal totalMRP = cart.getTotalMRP().add(cartItem.getItem().getActualPrice());
        BigDecimal totalDiscount = cart.getTotalDiscount().add(cartItem.getItem().getActualPrice().subtract(cartItem.getItem().getSellingPrice()));
        BigDecimal totalAmount = cart.getTotalAmount().add(cartItem.getItem().getSellingPrice());

        cart.setTotalMRP(totalMRP);
        cart.setTotalDiscount(totalDiscount);
        cart.setTotalAmount(totalAmount);
    }

}
