package com.example.demo.models;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecifications {
    public static Specification<Product> hasTitle(String title) {
        return (root, query, cb) -> {
            if (title == null) return null;
            String cleanedTitle = title.replace("\"", ""); // Удаляем кавычки
            return cb.like(root.get("title"), "%" + cleanedTitle + "%");
        };
    }

    public static Specification<Product> hasMinPrice(BigDecimal minPrice) {
        return (root, query, criteriaBuilder) ->
                minPrice == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> hasMaxPrice(BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) ->
                maxPrice == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, criteriaBuilder) ->
                categoryId == null ? null : criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }
}
