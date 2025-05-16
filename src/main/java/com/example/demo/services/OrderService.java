package com.example.demo.services;

import com.example.demo.dto.request.OrderRequest;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.models.MyUser;
import com.example.demo.models.Order;
import com.example.demo.models.OrderItem;
import com.example.demo.models.Product;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.OrderItemRepository;
import com.example.demo.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Map<String, Object> createOrder(MyUser user, OrderRequest orderRequest) {
        Map<String, Object> response = new HashMap<>();
        if (orderRequest.getItems() == null || orderRequest.getItems().isEmpty()) {
            throw new RuntimeException("Order items cannot be empty");
        }

        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .totalPrice(orderRequest.getTotalPrice())
                .build();

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = orderRequest.getItems().stream()
                .map(itemRequest -> {
                    Product product = productRepository.findById(itemRequest.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemRequest.getProductId()));

                    OrderItem orderItem = OrderItem.builder()
                            .order(savedOrder)
                            .product(product)
                            .quantity(itemRequest.getQuantity())
                            .priceAtPurchase(product.getPrice())
                            .build();
                    return orderItemRepository.save(orderItem);
                })
                .collect(Collectors.toList());

        savedOrder.setItems(orderItems);

        response.put("id", savedOrder.getId());
        response.put("status", "success");
        response.put("code", 201);
        return response;
    }

    public List<OrderResponse> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserIdWithItems(userId);

        return orders.stream().map(order -> {
            OrderResponse response = new OrderResponse();
            response.setId(order.getId());
            response.setOrderDate(order.getOrderDate());
            response.setTotalPrice(order.getTotalPrice());

            List<OrderResponse.OrderItemResponse> itemResponses = order.getItems().stream().map(item -> {
                OrderResponse.OrderItemResponse itemResponse = new OrderResponse.OrderItemResponse();
                itemResponse.setProductId(item.getProduct().getId());
                itemResponse.setProductName(item.getProduct().getTitle());
                itemResponse.setQuantity(item.getQuantity());
                itemResponse.setPriceAtPurchase(item.getPriceAtPurchase());
                return itemResponse;
            }).collect(Collectors.toList());

            response.setItems(itemResponses);
            return response;
        }).collect(Collectors.toList());
    }
}