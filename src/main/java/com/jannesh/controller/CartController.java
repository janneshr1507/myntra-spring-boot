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

    @PostMapping("/addItem")
    public ResponseEntity<?> addItemToCart(@RequestBody AddItemDTO addItemDTO) {
        return new ResponseEntity<>(cartService.addItemToCart(addItemDTO), HttpStatus.OK);
    }

    @GetMapping("/delete/{cartId}/{itemId}")
    public ResponseEntity<?> deleteItemFromCart(@PathVariable("cartId") UUID cartId, @PathVariable("itemId") UUID itemId) {
        cartService.removeCartItem(cartId, itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getCartList() {
        return new ResponseEntity<>(cartService.fetchCartList(), HttpStatus.OK);
    }

}
