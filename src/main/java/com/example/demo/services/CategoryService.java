package com.example.demo.services;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.response.CategoryResponseDTO;
import com.example.demo.dto.response.ProductResponseDTO;
import com.example.demo.exceptions.notfound.CategoryNotFoundException;
import com.example.demo.exceptions.DuplicateCategoryException;
import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    @Transactional
    public CategoryResponseDTO createCategory(CategoryDTO categoryDTO) {
        Map<String, List<String>> errors = new HashMap<>();
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            errors.put("category", List.of("category is exist: " + categoryDTO.getName()));
            throw new DuplicateCategoryException(errors);
        }
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .build();

        Category savedCategory = categoryRepository.save(category);
        return mapToCategoryResponseDTO(savedCategory);
    }

    public Page<CategoryResponseDTO> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(this::mapToCategoryResponseDTO);
    }

    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    Map<String, List<String>> errors = new HashMap<>();
                    errors.put("category", List.of("Category with id " + id + " not found"));
                    return new CategoryNotFoundException(errors);
                });
        return mapToCategoryResponseDTO(category);
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Map<String, List<String>> errors = new HashMap<>();
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    errors.put("category", List.of("Category with id " + id + " not found"));
                    return new CategoryNotFoundException(errors);
                });

        if (categoryRepository.existsByName(categoryDTO.getName())) {
            errors.put("category", List.of("category is exist: " + categoryDTO.getName()));
            throw new DuplicateCategoryException(errors);
        }

        category.setName(categoryDTO.getName());

        Category updatedCategory = categoryRepository.save(category);
        return mapToCategoryResponseDTO(updatedCategory);
    }

    public void deleteCategory(Long id) {
        Map<String, List<String>> errors = new HashMap<>();
        categoryRepository.findById(id)
                .orElseThrow(() -> {
                    errors.put("category", List.of("Category with id " + id + " not found"));
                    return new CategoryNotFoundException(errors);
                });

        categoryRepository.deleteById(id);
    }

    private CategoryResponseDTO mapToCategoryResponseDTO(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    private List<ProductResponseDTO> getProductsForCategory(Long categoryId) {
        return productService.getProductsByCategory(categoryId).stream()
                .map(productService::mapToProductResponseDTO)
                .collect(Collectors.toList());
    }
}