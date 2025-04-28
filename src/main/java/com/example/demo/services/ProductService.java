package com.example.demo.services;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.response.ProductResponseDTO;
import com.example.demo.exceptions.InvalidProductDataException;
import com.example.demo.exceptions.ProductCreationException;
import com.example.demo.exceptions.notfound.ProductNotFoundException;
import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductResponseDTO createProduct(ProductDTO productDTO) {
        Map<String, List<String>> errors = validateProductDTO(productDTO);

        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElse(null);

        if (category == null) {
            errors.put("category", List.of("Category with id " + productDTO.getCategoryId() + " not found"));
        }

        if (!errors.isEmpty()) {
            throw new InvalidProductDataException(errors);
        }

        try {
            Product product = Product.builder()
                    .title(productDTO.getTitle())
                    .description(productDTO.getDescription())
                    .price(productDTO.getPrice())
                    .imageUrl(productDTO.getImageUrl())
                    .category(category)
                    .build();

            Product savedProduct = productRepository.save(product);
            return mapToProductResponseDTO(savedProduct);
        } catch (DataIntegrityViolationException e) {
            Map<String, List<String>> error = new HashMap<>();
            error.put("product", List.of("Failed to create product due to database error"));
            throw new ProductCreationException(error);
        }
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    Map<String, List<String>> errors = new HashMap<>();
                    errors.put("id", List.of("Product with id " + id + " not found"));
                    return new ProductNotFoundException(errors);
                });
    }

    public ProductResponseDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    Map<String, List<String>> error = new HashMap<>();
                    error.put("product", List.of("Product with id " + id + " not found"));
                    return new ProductNotFoundException(error);
                });
        Map<String, List<String>> errors = validateProductDTO(productDTO);

        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElse(null);

        if (category == null) {
            errors.put("category", List.of("Category with id " + productDTO.getCategoryId() + " not found"));
        }

        if (!errors.isEmpty()) {
            throw new InvalidProductDataException(errors);
        }

        try {
            product.setTitle(productDTO.getTitle());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setImageUrl(productDTO.getImageUrl());
            product.setCategory(category);

            Product savedProduct = productRepository.save(product);
            return mapToProductResponseDTO(savedProduct);
        } catch (DataIntegrityViolationException e) {
            Map<String, List<String>> error = new HashMap<>();
            error.put("product", List.of("Failed to create product due to database error"));
            throw new ProductCreationException(error);
        }
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> {
                    Map<String, List<String>> error = new HashMap<>();
                    error.put("product", List.of("Product with id " + id + " not found"));
                    return new ProductNotFoundException(error);
                });
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public ProductResponseDTO mapToProductResponseDTO(Product product) {
        return ProductResponseDTO.builder()
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .categoryId(product.getCategory().getId())
                .build();
    }

    private Map<String, List<String>> validateProductDTO(ProductDTO productDTO) {
        Map<String, List<String>> errors = new HashMap<>();

        // Валидация названия
        if (productDTO.getTitle() == null || productDTO.getTitle().trim().isEmpty()) {
            errors.put("title", List.of("Product title cannot be empty"));
        } else if (productDTO.getTitle().length() > 100) {
            errors.put("title", List.of("Product title cannot exceed 100 characters"));
        }

        // Валидация описания
        if (productDTO.getDescription() != null && productDTO.getDescription().length() > 500) {
            errors.put("description", List.of("Product description cannot exceed 500 characters"));
        }

        // Валидация цены
        if (productDTO.getPrice() == null) {
            errors.put("price", List.of("Price is required"));
        } else if (productDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            errors.put("price", List.of("Price must be greater than 0"));
        } else if (productDTO.getPrice().scale() > 2) {
            errors.put("price", List.of("Price cannot have more than 2 decimal places"));
        }

        // Валидация URL изображения
        if (productDTO.getImageUrl() != null && !isValidUrl(productDTO.getImageUrl())) {
            errors.put("imageUrl", List.of("Invalid image URL format"));
        }

        // Валидация категории
        if (productDTO.getCategoryId() == null) {
            errors.put("category", List.of("Category ID is required"));
        }
        return errors;
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}