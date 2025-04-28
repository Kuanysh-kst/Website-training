package com.example.demo.repositories;

import com.example.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId); // Товары по категории
    List<Product> findByTitleContainingIgnoreCase(String title); // Поиск по названию
    Boolean existsByTitle(String title);
}