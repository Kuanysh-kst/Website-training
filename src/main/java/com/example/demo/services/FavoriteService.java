package com.example.demo.services;

import com.example.demo.models.*;
import com.example.demo.repositories.FavoriteRepository;
import com.example.demo.repositories.MyUserRepository;
import com.example.demo.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final ProductRepository productRepository;
    private final MyUserRepository userRepository;

    public List<Product> getUserFavorites(String email) {
        return favoriteRepository.findByUserEmail(email).stream()
                .map(Favorite::getProduct)
                .collect(Collectors.toList());
    }

    public Favorite addFavorite(String email, Long productId) {
        if (favoriteRepository.existsByUserEmailAndProductId(email, productId)) {
            throw new RuntimeException("Product already in favorites");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Favorite favorite = Favorite.builder()
                .user(userRepository.findByEmail(email).orElseThrow())
                .product(product)
                .build();

        return favoriteRepository.save(favorite);
    }

    public void removeFavorite(String email, Long productId) {
        favoriteRepository.deleteByUserEmailAndProductId(email, productId);
    }
}