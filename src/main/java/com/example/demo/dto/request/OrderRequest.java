package com.example.demo.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequest {
    private List<OrderItemRequest> items;
    private BigDecimal totalPrice;

    @Data
    public static class OrderItemRequest {
        private Long productId;
        private Integer quantity;
    }
}