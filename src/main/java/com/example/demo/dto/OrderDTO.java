package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private String status;
    private LocalDateTime orderDate;
    private List<OrderItemDTO> items;
    private BigDecimal total;
}
