package com.jannesh.controller;

import com.jannesh.dto.cart.AddItemDTO;
import com.jannesh.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/addItem")
    public ResponseEntity<?> addItemToCart(@RequestBody AddItemDTO addItemDTO) {
        return new ResponseEntity<>(cartService.addItemToCart(addItemDTO), HttpStatus.OK);
    }
}
