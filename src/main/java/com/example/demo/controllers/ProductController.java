package com.example.demo.controllers;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.response.ProductResponseDTO;
import com.example.demo.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(Pageable pageable,
                                            @RequestParam(required = false) String title,
                                            @RequestParam(required = false) BigDecimal minPrice,
                                            @RequestParam(required = false) BigDecimal maxPrice,
                                            @RequestParam(required = false) Long categoryId) {
        return ResponseEntity.ok(productService.getAllProducts(pageable, title, minPrice, maxPrice, categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/add-product")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDTO productDTO
    ) {
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}