package com.example.demo.services;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.response.ProductResponseDTO;
import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    String title;
    String description;
    BigDecimal price;
    String imageUrl;
    Long categoryId;
    String categoryName;

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
        categoryName = "Mock category";
    }

    @Test
    void testCreateProduct_WhenProductDetailsProvided_returnsProductObject() {
        //Arrange
        ProductDTO productDTOMock = new ProductDTO(title, description, price, imageUrl, categoryId);

        Category mockCategory = new Category(categoryId,categoryName);
        Mockito.when(mockCategoryRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(mockCategory));

        Product savedProduct = new Product(1L, title, description, price, imageUrl, mockCategory);
        Mockito.when(mockProductRepository.save(Mockito.any(Product.class))).thenReturn(savedProduct);

        ProductResponseDTO expectedResponse = new ProductResponseDTO(title, description, price, imageUrl, categoryId);

        // Act
        ProductResponseDTO mockProductResponseDTO = productService.createProduct(productDTOMock);

        // Assert
        Assertions.assertEquals(expectedResponse, mockProductResponseDTO);
    }
}
