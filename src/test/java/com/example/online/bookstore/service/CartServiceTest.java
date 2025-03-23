package com.example.online.bookstore.service;

import com.example.online.bookstore.dto.CartItemDTO;
import com.example.online.bookstore.model.Book;
import com.example.online.bookstore.model.CartItem;
import com.example.online.bookstore.model.User;
import com.example.online.bookstore.repository.CartItemRepository;
import com.example.online.bookstore.repository.UserRepository;
import com.example.online.bookstore.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookService bookService;

    @InjectMocks
    private CartServiceImpl cartService;

    private User testUser;
    private Book testBook;
    private CartItem testCartItem;
    private CartItemDTO testCartItemDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");

        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test Book");
        testBook.setPrice(29.99);

        testCartItem = new CartItem();
        testCartItem.setId(1L);
        testCartItem.setUser(testUser);
        testCartItem.setBook(testBook);
        testCartItem.setQuantity(1);

        testCartItemDTO = new CartItemDTO();
        testCartItemDTO.setBookId(1L);
        testCartItemDTO.setQuantity(1);
    }

    @Test
    void addToCart_ShouldCreateNewCartItem() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(bookService.getBookById(1L)).thenReturn(testBook);
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(testCartItem);

        CartItem result = cartService.addToCart(1L, 1L, 1);

        assertNotNull(result);
        assertEquals(testBook, result.getBook());
        assertEquals(testUser, result.getUser());
        assertEquals(1, result.getQuantity());
    }

    @Test
    void getCartItems_ShouldReturnUserItems() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(cartItemRepository.findByUser(testUser)).thenReturn(Arrays.asList(testCartItem));

        List<CartItem> result = cartService.getCartItems(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCartItem, result.get(0));
    }

    @Test
    void removeFromCart_ShouldDeleteCartItem() {
        when(cartItemRepository.findById(1L)).thenReturn(Optional.of(testCartItem));

        assertDoesNotThrow(() -> cartService.removeFromCart(1L, 1L));
        verify(cartItemRepository).delete(testCartItem);
    }

    @Test
    void removeFromCart_ShouldThrowException_WhenUnauthorized() {
        testCartItem.getUser().setId(2L); // Different user
        when(cartItemRepository.findById(1L)).thenReturn(Optional.of(testCartItem));

        assertThrows(RuntimeException.class, () -> cartService.removeFromCart(1L, 1L));
    }

    @Test
    void clearCart_ShouldDeleteAllUserItems() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(cartItemRepository.findByUser(testUser)).thenReturn(Arrays.asList(testCartItem));

        assertDoesNotThrow(() -> cartService.clearCart(1L));
        verify(cartItemRepository).deleteAll(anyList());
    }
}