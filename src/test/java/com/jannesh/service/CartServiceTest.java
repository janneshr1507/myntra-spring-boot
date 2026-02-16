package com.jannesh.service;

import com.jannesh.entity.Cart;
import com.jannesh.entity.CartItem;
import com.jannesh.entity.Item;
import com.jannesh.repository.CartItemRepository;
import com.jannesh.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartItemRepository cartItemRepo;
    @Mock
    private CartRepository cartRepo;
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

        when(cartItemRepo.findByCart_CartId(cartId)).thenReturn(cartItemList);
        when(cartRepo.save(any(Cart.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Cart savedCart = cartService.calculateCartAmount(cart);

        assertEquals(BigDecimal.valueOf(800.0), savedCart.getTotalDiscount());

        verify(cartItemRepo, times(1)).findByCart_CartId(cartId);
        verify(cartRepo, times(1)).save(any(Cart.class));
    }

    @Test
    public void shouldCalculateEmptyCartAmountSuccessfully() {
        UUID cartId = UUID.randomUUID();

        Cart cart = new Cart();
        cart.setCartId(cartId);

        when(cartItemRepo.findByCart_CartId(cartId)).thenReturn(List.of());
        when(cartRepo.save(any(Cart.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Cart savedCart = cartService.calculateCartAmount(cart);

        assertEquals(BigDecimal.ZERO, savedCart.getTotalDiscount());
    }
}
