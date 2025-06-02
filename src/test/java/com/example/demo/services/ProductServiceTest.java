package com.example.demo.services;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.response.ProductResponseDTO;
import com.example.demo.exceptions.InvalidProductDataException;
import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    String title;
    String description;
    BigDecimal price;
    String imageUrl;
    Long categoryId;
    Category category;

    @InjectMocks
    private ProductService productService;

    @Mock
    private CategoryRepository mockCategoryRepository;
    @Mock
    private ProductRepository mockProductRepository;


    @BeforeEach
    void init() {
        title = "test product";
        description = "test description";
        price = new BigDecimal("1000000");
        imageUrl = "https://www.google.com/search?sca_esv=e959774ba9715974&sxsrf=AE3TifPHX3fnj7_JeMK0F1kIQ3CPvnD4-A:1748537005410&q=acer+nitro+5+an515-57+drivers&udm=2&fbs=AIIjpHy3vMFde4-A-s4rkZ3m7V6OBOGFvzWdNs_AW9AWwX0uEr0-Kz8D1ggHfcxRhV96GyEzhFKKgWiDCPxEDWuHlaSnjKih7Fu1ZyY764BeAfZttjfOhT9Q6awKs-M0MauHyO0oOPtxrVNbIHyAqs-9z7h9mdPLQ5Rr66wmKXs3k3ByGFr5KfhEtf3DF0RcXUNlGxIp-5piP-4zMlJv7e9X-1qB_QW0E7SufVoZ2_YDf298sZYqgzyVloBeUw67woktbYS-sYMZ4kTfXPMLD2Eul6QitTsiRA&sa=X&ved=2ahUKEwjY-dX4j8mNAxU9QFUIHbGiKxgQtKgLegQIFRAB&biw=1536&bih=731&dpr=1.25#vhid=_8PQbwzfNUwXPM&vssid=mosaic";
        categoryId = 1L;
        category = new Category(1L, "Mock category");
    }

    @Test
    void testCreateProduct_whenProductDetailsProvided_returnsProductObject() {
        //Arrange
        ProductDTO productDTOMock = new ProductDTO(title, description, price, imageUrl, categoryId);

        when(mockCategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(category));

        Product savedProduct = new Product(1L, title, description, price, imageUrl, category);
        when(mockProductRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductResponseDTO expectedResponse = new ProductResponseDTO(title, description, price, imageUrl, categoryId);

        // Act
        ProductResponseDTO actualProductResponseDTO = productService.createProduct(productDTOMock);

        // Assert
        assertNotNull(actualProductResponseDTO);
        assertEquals(expectedResponse, actualProductResponseDTO);
        assertEquals(title, actualProductResponseDTO.getTitle());
        assertEquals(description, actualProductResponseDTO.getDescription());
        assertEquals(price, actualProductResponseDTO.getPrice());
        assertEquals(imageUrl, actualProductResponseDTO.getImageUrl());
        assertEquals(categoryId, actualProductResponseDTO.getCategoryId());
        verify(mockCategoryRepository).findById(any(Long.class));
        verify(mockProductRepository).save(any(Product.class));
    }

    @Test
    void testCreateProduct_whenTitleIsEmpty_throwInvalidProductDataException() {
        //Arrange
        ProductDTO emptyTitleProductDto = new ProductDTO(null, description, price, imageUrl, categoryId);

        when(mockCategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(category));
        // Act
        InvalidProductDataException actualError = assertThrows(InvalidProductDataException.class,
                () -> productService.createProduct(emptyTitleProductDto),
                "Empty title should have caused an Invalid Product Argument Exception");
        Map<String, List<String>> error = new HashMap<>();
        error.put("title", List.of("Product title cannot be empty"));
        assertEquals(error, actualError.getErrors());
    }

    @Test
    void testCreateProduct_whenDescriptionExceedLimit_throwInvalidProductDataException() {
        //Arrange
        ProductDTO emptyDescriptionProductDto = new ProductDTO(title, "x".repeat(501), price, imageUrl, categoryId);
        when(mockCategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(category));
        // Act
        InvalidProductDataException actualError = assertThrows(InvalidProductDataException.class,
                () -> productService.createProduct(emptyDescriptionProductDto),
                "Empty Description should have caused an Invalid Product Argument Exception");
        Map<String, List<String>> error = new HashMap<>();
        error.put("description", List.of("Product description cannot exceed 500 characters"));
        assertEquals(error, actualError.getErrors());
    }

    @Test
    void testCreateProduct_whenPriceIsEmpty_throwInvalidProductDataException() {
        //Arrange
        ProductDTO overLimitDescriptionProductDto = new ProductDTO(title, description, null, imageUrl, categoryId);
        when(mockCategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(category));
        // Act
        InvalidProductDataException actualError = assertThrows(InvalidProductDataException.class,
                () -> productService.createProduct(overLimitDescriptionProductDto),
                "Empty Price should have caused an Invalid Product Argument Exception");
        Map<String, List<String>> error = new HashMap<>();
        error.put("price", List.of("Price is required"));
        assertEquals(error, actualError.getErrors());
    }

    @Test
    void testCreateProduct_whenPriceIsMinus_throwInvalidProductDataException() {
        //Arrange
        ProductDTO emptyDescriptionProductDto = new ProductDTO(title, description, new BigDecimal("-1"), imageUrl, categoryId);
        when(mockCategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(category));
        // Act
        InvalidProductDataException actualError = assertThrows(InvalidProductDataException.class,
                () -> productService.createProduct(emptyDescriptionProductDto),
                "Minus Price should have caused an Invalid Product Argument Exception");
        Map<String, List<String>> error = new HashMap<>();
        error.put("price", List.of("Price must be greater than 0"));
        assertEquals(error, actualError.getErrors());
    }

    @Test
    void testCreateProduct_whenPriceHasMoreScale_throwInvalidProductDataException() {
        //Arrange
        ProductDTO emptyDescriptionProductDto = new ProductDTO(title, description, new BigDecimal("1.111"), imageUrl, categoryId);
        when(mockCategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(category));
        // Act
        InvalidProductDataException actualError = assertThrows(InvalidProductDataException.class,
                () -> productService.createProduct(emptyDescriptionProductDto),
                "More scale Price should have caused an Invalid Product Argument Exception");
        Map<String, List<String>> error = new HashMap<>();
        error.put("price", List.of("Price cannot have more than 2 decimal places"));
        assertEquals(error, actualError.getErrors());
    }

    @Test
    void testCreateProduct_whenImageUrlIsInvalid_throwInvalidProductDataException() {
        //Arrange
        String invalidImageUrl = "htdp:/invalid/url";
        ProductDTO emptyDescriptionProductDto = new ProductDTO(title, description, price, invalidImageUrl, categoryId);
        when(mockCategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(category));
        // Act
        InvalidProductDataException actualError = assertThrows(InvalidProductDataException.class,
                () -> productService.createProduct(emptyDescriptionProductDto),
                "Invalid Image url should have caused an Invalid Product Argument Exception");
        Map<String, List<String>> error = new HashMap<>();
        error.put("imageUrl", List.of("Invalid image URL format"));
        assertEquals(error, actualError.getErrors());
    }

    @Test
    void testCreateProduct_whenCategoryIdIsEmpty_throwInvalidProductDataException() {
        //Arrange
        ProductDTO emptyDescriptionProductDto = new ProductDTO(title, description, price, imageUrl, null);
        // Act
        InvalidProductDataException actualError = assertThrows(InvalidProductDataException.class,
                () -> productService.createProduct(emptyDescriptionProductDto),
                "Empty category id should have caused an Invalid Product Argument Exception");
        Map<String, List<String>> error = new HashMap<>();
        error.put("category", List.of("Category with id null not found"));
        assertEquals(error, actualError.getErrors());
    }

    @Test
    void testCreateProduct_whenProductDtoIsEmpty_throwInvalidProductDataException() {
        InvalidProductDataException actualError = assertThrows(InvalidProductDataException.class,
                () -> productService.createProduct(null),
                "Empty product dto should have caused an Invalid Product Argument Exception");
        Map<String, List<String>> error = new HashMap<>();
        error.put("product", List.of("Product is Empty"));
        assertEquals(error, actualError.getErrors());
    }

}
