package com.example.demo.repositories;

import com.example.demo.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    // 1. Найти все избранные товары пользователя
    List<Favorite> findByUserEmail(String email);

    // 2. Найти конкретный товар в избранном пользователя
    Optional<Favorite> findByUserIdAndProductId(Long userId, Long productId);

    // 3. Проверить, есть ли товар в избранном
    boolean existsByUserEmailAndProductId(String email, Long productId);

    // 4. Удалить товар из избранного
    @Transactional
    @Modifying
    @Query("DELETE FROM Favorite f WHERE f.user.email = :email AND f.product.id = :productId")
    void deleteByUserEmailAndProductId(String email, Long productId);

    // 5. Удалить все избранные товары пользователя
    @Transactional
    @Modifying
    void deleteAllByUserEmail(String email);

    // 6. Количество избранных товаров пользователя
    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.user.email = :email")
    int countByUserEmail(String email);
}