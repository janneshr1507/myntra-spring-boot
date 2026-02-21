package com.jannesh.controller;

import com.jannesh.dto.cart.AddItemDTO;
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

    @GetMapping("/get/{cartId}")
    public ResponseEntity<?> getCartDTOByCartId(@PathVariable("cartId") UUID cartId) {
        return new ResponseEntity<>(cartService.fetchCartDTOByCartId(cartId), HttpStatus.OK);
    }

    @PostMapping("/addItem")
    public ResponseEntity<?> addItemToCart(@RequestBody AddItemDTO addItemDTO) {
        return new ResponseEntity<>(cartService.addItemToCart(addItemDTO), HttpStatus.OK);
    }

    @GetMapping("/delete/{cartItemId}")
    public ResponseEntity<?> deleteItemFromCart(@PathVariable("cartItemId") UUID cartItemId) {
        return new ResponseEntity<>(cartService.removeItemFromCart(cartItemId), HttpStatus.OK);
    }

}
