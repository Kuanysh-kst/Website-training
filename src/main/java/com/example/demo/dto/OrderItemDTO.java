package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal priceAtPurchase;
}