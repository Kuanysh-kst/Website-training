package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// ProductResponseDTO.java (дополнительные поля для ответа)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private String title;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Long categoryId;
}