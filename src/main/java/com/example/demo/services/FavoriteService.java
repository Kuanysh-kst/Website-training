package com.example.demo.services;

import com.example.demo.dto.response.FavoriteDTO;
import com.example.demo.exceptions.duplicate.ProductDuplicateException;
import com.example.demo.exceptions.notfound.ProductNotFoundException;
import com.example.demo.models.Favorite;
import com.example.demo.models.Product;
import com.example.demo.repositories.FavoriteRepository;
import com.example.demo.repositories.MyUserRepository;
import com.example.demo.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final ProductRepository productRepository;
    private final MyUserRepository userRepository;

    public List<FavoriteDTO> getUserFavorites(String email) {
        return favoriteRepository.findByUserEmail(email)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public String addFavorite(String email, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    Map<String, List<String>> error = new HashMap<>();
                    error.put("product", List.of("Product with id " + productId + " not found"));
                    return new ProductNotFoundException(error);
                });

        if (favoriteRepository.existsByUserEmailAndProductId(email, productId)) {
            Map<String, List<String>> errors = new HashMap<>();
            errors.put("favorite", List.of("Product with id: " + productId + " already in favorites"));
            throw new ProductDuplicateException(errors);
        }

        Favorite favorite = Favorite.builder()
                .user(userRepository.findByEmail(email).orElseThrow())
                .product(product)
                .build();

        favoriteRepository.save(favorite);

        return "Added to favorites";
    }

    public void removeFavorite(String email, Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(Map.of("product", List.of("Product not found")));
        }

        if (!favoriteRepository.existsByUserEmailAndProductId(email, productId)) {
            Map<String, List<String>> errors = new HashMap<>();
            errors.put("favorite", List.of("Product with id: " + productId + " not yet in favorites"));
            throw new ProductDuplicateException(errors);
        }
        favoriteRepository.deleteByUserEmailAndProductId(email, productId);
    }

    private FavoriteDTO convertToDto(Favorite favorite) {
        return new FavoriteDTO(
                favorite.getId(),
                favorite.getUser().getId(),
                favorite.getProduct().getId()
        );
    }

    public List<Product> getFavoriteProductsByUserEmail(String email) {
        return favoriteRepository.findProductsByUserEmail(email);
    }
}