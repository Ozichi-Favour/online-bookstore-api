package com.example.online.bookstore.service.impl;


import com.example.online.bookstore.model.Book;
import com.example.online.bookstore.model.CartItem;
import com.example.online.bookstore.model.User;
import com.example.online.bookstore.repository.CartItemRepository;
import com.example.online.bookstore.repository.UserRepository;
import com.example.online.bookstore.service.BookService;
import com.example.online.bookstore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final BookService bookService;

    @Override
    @Transactional
    public CartItem addToCart(Long userId, Long bookId, Integer quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookService.getBookById(bookId);

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> getCartItems(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartItemRepository.findByUser(user);
    }

    @Override
    @Transactional
    public void removeFromCart(Long userId, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cartItem.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to cart item");
        }

        cartItemRepository.delete(cartItem);
    }
    @Override
    @Transactional
    public void clearCart(Long userId) {
        List<CartItem> cartItems = getCartItems(userId);
        cartItemRepository.deleteAll(cartItems);
    }
}