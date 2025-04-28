package com.example.demo.dto;

// FavoriteDTO.java

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FavoriteDTO {
    private Long productId;
    private String productTitle;
    private BigDecimal price;
    private String imageUrl;
}