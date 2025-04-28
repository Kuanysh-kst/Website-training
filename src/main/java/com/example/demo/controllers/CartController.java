package com.example.demo.controllers;

import com.example.demo.models.MyUser;
import com.example.demo.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<?> getCart(@AuthenticationPrincipal MyUser user) {
        return ResponseEntity.ok(cartService.getCartByUser(user));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @AuthenticationPrincipal MyUser user,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity
    ) {
        return ResponseEntity.ok(cartService.addToCart(user.getEmail(), productId, quantity));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @AuthenticationPrincipal MyUser user,
            @PathVariable Long productId
    ) {
        cartService.removeFromCart(user.getId(), productId);
        return ResponseEntity.noContent().build();
    }
}