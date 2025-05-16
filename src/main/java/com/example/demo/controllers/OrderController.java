package com.example.demo.controllers;

import com.example.demo.dto.request.OrderRequest;
import com.example.demo.models.MyUser;
import com.example.demo.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@AuthenticationPrincipal MyUser user,
                                         @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(user, orderRequest));
    }

    @GetMapping
    public ResponseEntity<?> getUserOrders(@AuthenticationPrincipal MyUser user) {
        return ResponseEntity.ok(orderService.getUserOrders(user.getId()));
    }
}