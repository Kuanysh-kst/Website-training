package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class ProductDTO {
    private String title;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Long categoryId;
}

