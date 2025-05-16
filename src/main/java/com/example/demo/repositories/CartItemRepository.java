package com.example.demo.repositories;

import com.example.demo.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Найти все элементы корзины по ID корзины
    List<CartItem> findByCartId(Long cartId);

    // Найти конкретный элемент корзины по ID корзины и ID товара
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    // Удалить элемент корзины по ID корзины и ID товара
    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId")
    void deleteByCartIdAndProductId(Long cartId, Long productId);

    // Удалить все элементы корзины по ID корзины
    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    void deleteByCartId(Long cartId);

    // Проверить существование товара в корзине
    boolean existsByCartIdAndProductId(Long cartId, Long productId);

    // Обновить количество товара в корзине
    @Transactional
    @Modifying
    @Query("UPDATE CartItem ci SET ci.quantity = :quantity WHERE ci.id = :id")
    void updateQuantity(Long id, int quantity);
}