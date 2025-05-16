package com.example.demo.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItemDTO {
    private Long productId;
    private String productTitle;
    private BigDecimal price;
    private int quantity;
    private String imageUrl;
}

