package com.jannesh.controller;

import com.jannesh.dto.cart.AddItemDTO;
import com.jannesh.dto.cartItem.UpdateCartItemDTO;
import com.jannesh.service.CartItemService;
import com.jannesh.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;

    @PostMapping("/addItem")
    public ResponseEntity<?> addItemToCart(@RequestBody AddItemDTO addItemDTO) {
        return new ResponseEntity<>(cartService.addItemToCart(addItemDTO), HttpStatus.OK);
    }

    @PostMapping("/updateItem")
    public ResponseEntity<?> updateItemInCart(@RequestBody UpdateCartItemDTO  updateCartItemDTO) {
        return new ResponseEntity<>(cartItemService.updateCartItemDTO(updateCartItemDTO), HttpStatus.OK);
    }

    @GetMapping("/delete/{cartId}/{itemId}")
    public ResponseEntity<?> deleteItemFromCart(@PathVariable("cartId") UUID cartId, @PathVariable("itemId") UUID itemId) {
        cartService.removeItemFromCart(cartId, itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getCartList() {
        return new ResponseEntity<>(cartService.fetchCartList(), HttpStatus.OK);
    }

    @GetMapping("/getByCartId/{cartId}")
    public ResponseEntity<?> getCartDTOByCartId(@PathVariable("cartId") UUID cartId) {
        return new ResponseEntity<>(cartService.fetchCartDTOByCartId(cartId), HttpStatus.OK);
    }

}
