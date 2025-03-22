package com.example.online.bookstore.repository;

import com.example.online.bookstore.model.CartItem;
import com.example.online.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
}