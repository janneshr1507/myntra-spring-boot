package com.jannesh.service;

import com.jannesh.entity.Cart;
import com.jannesh.entity.CartItem;
import com.jannesh.entity.Inventory;
import com.jannesh.entity.Item;
import com.jannesh.repository.CartItemRepository;
import com.jannesh.repository.CartRepository;
import com.jannesh.util.mapper.CartItemMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartItemRepository cartItemRepo;
    @Mock
    private CartRepository cartRepo;
    @Mock
    private InventoryService inventoryService;
    @Mock
    private CustomerService customerService;
    @Mock
    private ItemService itemService;
    @Mock
    private CartItemMapper cartItemMapper;
    @InjectMocks
    private CartService cartService;

    @Test
    public void shouldCalculateCartAmountSuccessfully() {
        UUID itemId = UUID.randomUUID();
        UUID secondItemId = UUID.randomUUID();
        UUID cartId = UUID.randomUUID();

        Item item = new Item();
        item.setItemId(itemId);
        item.setActualPrice(BigDecimal.valueOf(1000));
        item.setDiscount(BigDecimal.valueOf(0.1));
        item.setSellingPrice(BigDecimal.valueOf(900));

        Item secondItem = new Item();
        secondItem.setItemId(secondItemId);
        secondItem.setActualPrice(BigDecimal.valueOf(3000));
        secondItem.setDiscount(BigDecimal.valueOf(0.1));
        secondItem.setSellingPrice(BigDecimal.valueOf(2700));

        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        cartItem.setQuantity(2L);

        CartItem secondCartItem = new CartItem();
        secondCartItem.setItem(secondItem);
        secondCartItem.setQuantity(2L);

        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem);
        cartItemList.add(secondCartItem);

        Cart cart = new Cart();
        cart.setCartId(cartId);

        when(cartItemRepo.findByCart_CartIdWithItem(cartId)).thenReturn(cartItemList);
        when(cartRepo.save(any(Cart.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Cart savedCart = cartService.calculateCartAmount(cart);

        assertEquals(BigDecimal.valueOf(800.0), savedCart.getTotalDiscount());

        verify(cartItemRepo, times(1)).findByCart_CartIdWithItem(cartId);
        verify(cartRepo, times(1)).save(any(Cart.class));
    }

    @Test
    public void shouldCalculateEmptyCartAmountSuccessfully() {
        UUID cartId = UUID.randomUUID();

        Cart cart = new Cart();
        cart.setCartId(cartId);

        when(cartItemRepo.findByCart_CartIdWithItem(cartId)).thenReturn(List.of());
        when(cartRepo.save(any(Cart.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Cart savedCart = cartService.calculateCartAmount(cart);

        assertEquals(BigDecimal.ZERO, savedCart.getTotalDiscount());
    }

    @Test
    public void shouldRemoveItemFromCartSuccessfully() {
        UUID cartId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        UUID cartItemId = UUID.randomUUID();

        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(cartItemId);
        cartItem.setQuantity(10L);

        Inventory inventory = new Inventory();
        inventory.setAvailableQty(10L);
        inventory.setReservedQty(10L);

        when(cartItemRepo.findByCart_CartIdAndItem_ItemIdWithItem(cartId, itemId)).thenReturn(Optional.of(cartItem));
        when(inventoryService.fetchInventoryByItemId(itemId)).thenReturn(inventory);

        cartService.removeItemFromCart(cartId, itemId);

        assertEquals(20L, inventory.getAvailableQty());
        assertEquals(0L, inventory.getReservedQty());
    }

    @Test
    public void shouldThrowErrorWhenTryingToRemoveItemFromCartWhenNotFound() {
        UUID cartId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();

        when(cartItemRepo.findByCart_CartIdAndItem_ItemIdWithItem(cartId, itemId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
           cartService.removeItemFromCart(cartId, itemId);
        });

        assertEquals("Cart Item not found", ex.getMessage());
    }

    /*@Test
    public void shouldAddItemToCartSuccessfully() {
        UUID customerId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        UUID inventoryId = UUID.randomUUID();
        UUID cartId = UUID.randomUUID();

        Item item = new Item();
        item.setItemId(itemId);
        item.setSellingPrice(BigDecimal.valueOf(100));

        Inventory inventory = new Inventory();
        inventory.setInventoryId(inventoryId);
        inventory.setAvailableQty(10L);
        inventory.setReservedQty(0L);

        Cart cart = new Cart();
        cart.setCartId(cartId);

        when(customerService.existsByCustomerId(customerId)).thenReturn(true);
        when(itemService.fetchItemByItemId(itemId)).thenReturn(item);
        when(inventoryService.fetchInventoryByItemId(itemId)).thenReturn(inventory);
        when(cartRepo.findByCustomer_CustomerIdAndStatus(customerId, CartStatus.OPEN)).thenReturn(Optional.of(cart));
        when(cartItemRepo.save(any(CartItem.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(cartItemMapper.toDTO(any(CartItem.class))).thenAnswer(invocation -> {
           CartItem savedCartItem = invocation.getArgument(0);
           CartItemDTO cartItemDTO = new CartItemDTO();
           cartItemDTO.setQuantity(savedCartItem.getQuantity());
           cartItemDTO.setAmount(savedCartItem.getAmount());
           return cartItemDTO;
        });

        AddItemDTO requestDTO = new AddItemDTO();
        requestDTO.setCustomerId(customerId);
        requestDTO.setItemId(itemId);
        requestDTO.setQuantity(10L);

        CartItemDTO savedCartItemDTO = cartService.addItemToCart(requestDTO);

        assertEquals(0L, inventory.getAvailableQty());
        assertEquals(10L, inventory.getReservedQty());
        assertEquals(10L, savedCartItemDTO.getQuantity());
        assertEquals(BigDecimal.valueOf(100), savedCartItemDTO.getAmount());
    }*/
}
