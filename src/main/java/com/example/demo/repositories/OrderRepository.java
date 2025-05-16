package com.example.demo.repositories;

import com.example.demo.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId); // Заказы пользователя
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.items WHERE o.user.id = :userId")
    List<Order> findByUserIdWithItems(Long userId);
}