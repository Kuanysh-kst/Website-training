package com.example.demo.controllers;

import com.example.demo.models.MyUser;
import com.example.demo.services.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<?> getFavorites(@AuthenticationPrincipal MyUser user) {
        return ResponseEntity.ok(favoriteService.getUserFavorites(user.getEmail()));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<?> addFavorite(
            @AuthenticationPrincipal MyUser user,
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(favoriteService.addFavorite(user.getEmail(), productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> removeFavorite(
            @AuthenticationPrincipal MyUser user,
            @PathVariable Long productId
    ) {
        favoriteService.removeFavorite(user.getEmail(), productId);
        return ResponseEntity.noContent().build();
    }
}