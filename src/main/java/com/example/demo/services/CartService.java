package com.example.demo.services;

import com.example.demo.models.Cart;
import com.example.demo.models.CartItem;
import com.example.demo.models.MyUser;
import com.example.demo.models.Product;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.CartItemRepository;
import com.example.demo.repositories.MyUserRepository;
import com.example.demo.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final MyUserRepository userRepository;

    public Cart getCartByUser(MyUser user) {
        return cartRepository.findByUserEmail(user.getEmail())
                .orElseGet(() -> createNewCart(user));
    }

    private Cart createNewCart(MyUser user) {
        Cart cart = Cart.builder().user(user).build();
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart addToCart(String email, Long productId, int quantity) {
        Cart cart = getCartByUser(userRepository.findByEmail(email).orElseThrow());
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .build();
            cart.getItems().add(newItem);
        }

        return cartRepository.save(cart);
    }

    public void removeFromCart(Long cartId, Long productId) {
        cartItemRepository.deleteByCartIdAndProductId(cartId, productId);
    }

    @Transactional
    public void clearCart(Long cartId) {
        cartItemRepository.deleteByCartId(cartId);
    }
}