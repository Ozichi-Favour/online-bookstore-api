package com.example.online.bookstore.controller;


import com.example.online.bookstore.model.Book;
import com.example.online.bookstore.model.CartItem;
import com.example.online.bookstore.model.User;
import com.example.online.bookstore.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    private CartItem testCartItem;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setPrice(29.99);

        testCartItem = new CartItem();
        testCartItem.setId(1L);
        testCartItem.setUser(user);
        testCartItem.setBook(book);
        testCartItem.setQuantity(1);
    }

    @Test
    void addToCart_ShouldReturnCartItem() throws Exception {
        when(cartService.addToCart(eq(1L), eq(1L), eq(1)))
                .thenReturn(testCartItem);

        mockMvc.perform(post("/api/cart/1/items")
                        .param("bookId", "1")
                        .param("quantity", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.quantity").value(1));
    }

    @Test
    void getCartItems_ShouldReturnItems() throws Exception {
        when(cartService.getCartItems(1L))
                .thenReturn(Arrays.asList(testCartItem));

        mockMvc.perform(get("/api/cart/1/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].quantity").value(1));
    }
}