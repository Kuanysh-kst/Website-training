package com.example.demo.services;

import com.example.demo.exceptions.duplicate.ProductDuplicateException;
import com.example.demo.exceptions.notfound.ProductNotFoundException;
import com.example.demo.models.Favorite;
import com.example.demo.models.Product;
import com.example.demo.repositories.FavoriteRepository;
import com.example.demo.repositories.MyUserRepository;
import com.example.demo.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public String addFavorite(String email, Long productId) {
        Product product = existingProduct(productId);
        isProductDuplicated(email, productId);

        Favorite favorite = Favorite.builder()
                .user(userRepository.findByEmail(email).orElseThrow())
                .product(product)
                .build();

        favoriteRepository.save(favorite);

        return "Added to favorites";
    }

    private void isProductDuplicated(String email, Long productId) {
        if (favoriteRepository.existsByUserEmailAndProductId(email, productId)) {
            Map<String, List<String>> errors = new HashMap<>();
            errors.put("favorite", List.of("Product with id: " + productId + " already in favorites"));
            throw new ProductDuplicateException(errors);
        }
    }

    private Product existingProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> {
                    Map<String, List<String>> error = new HashMap<>();
                    error.put("product", List.of("Product with id " + productId + " not found"));
                    return new ProductNotFoundException(error);
                });
    }

    public void removeFavorite(String email, Long productId) {
        existingProduct(productId);
        favoriteRepository.deleteByUserEmailAndProductId(email, productId);
    }
}