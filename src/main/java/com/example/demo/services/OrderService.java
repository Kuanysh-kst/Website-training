package com.example.demo.services;

import com.example.demo.models.*;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.OrderItemRepository;
import com.example.demo.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;

    @Transactional
    public Order createOrder(MyUser user) {
        Cart cart = cartService.getCartByUser(user);
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .status("PENDING")
                .build();

        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    OrderItem orderItem = OrderItem.builder()
                            .order(savedOrder)
                            .product(product)
                            .quantity(cartItem.getQuantity())
                            .priceAtPurchase(product.getPrice())
                            .build();
                    return orderItemRepository.save(orderItem);
                })
                .collect(Collectors.toList());

        savedOrder.setItems(orderItems);
        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}