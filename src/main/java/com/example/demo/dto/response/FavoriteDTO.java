package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FavoriteDTO {
    private Long id;
    private Long userId;
    private Long productId;
}
