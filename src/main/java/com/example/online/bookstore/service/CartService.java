package com.example.online.bookstore.service;

import com.example.online.bookstore.model.CartItem;

import java.util.List;

public interface CartService {
    CartItem addToCart(Long userId, Long bookId, Integer quantity);
    List<CartItem> getCartItems(Long userId);
    void removeFromCart(Long userId, Long cartItemId);
    void clearCart(Long userId);
}