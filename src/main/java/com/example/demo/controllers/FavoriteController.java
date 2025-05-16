package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.services.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<?> getFavorites(Authentication authentication) {
        String email = authentication.getName(); // Получаем email (username)
        return ResponseEntity.ok(favoriteService.getUserFavorites(email));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<?> addFavorite(
            Authentication authentication,
            @PathVariable Long productId
    ) {
        String email = authentication.getName();
        return ResponseEntity.ok(favoriteService.addFavorite(email, productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> removeFavorite(
            Authentication authentication,
            @PathVariable Long productId
    ) {
        String email = authentication.getName();
        favoriteService.removeFavorite(email, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getFavoriteProducts(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(favoriteService.getFavoriteProductsByUserEmail(email));
    }
}