package com.example.demo.dto.response;

import com.example.demo.dto.CartItemDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponseDTO {
    private List<CartItemDTO> items;
    private BigDecimal totalPrice;
}