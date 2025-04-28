package com.example.demo.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    private String title;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Long categoryId;
}

