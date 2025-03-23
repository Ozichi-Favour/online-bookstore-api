package com.example.online.bookstore.service;

import com.example.online.bookstore.dto.CheckoutRequestDTO;
import com.example.online.bookstore.dto.OrderResponseDTO;
import com.example.online.bookstore.enums.OrderStatus;
import com.example.online.bookstore.enums.PaymentMethod;
import com.example.online.bookstore.model.*;
import com.example.online.bookstore.repository.OrderRepository;
import com.example.online.bookstore.repository.UserRepository;
import com.example.online.bookstore.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Spy;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartService cartService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private User testUser;
    private Book testBook;
    private CartItem testCartItem;
    private Order testOrder;
    private OrderItem testOrderItem;
    private CheckoutRequestDTO testCheckoutRequest;

    @BeforeEach
    void setUp() {
        // Set up test user
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");

        // Set up test book
        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test Book");
        testBook.setPrice(29.99);

        // Set up test cart item
        testCartItem = new CartItem();
        testCartItem.setId(1L);
        testCartItem.setUser(testUser);
        testCartItem.setBook(testBook);
        testCartItem.setQuantity(1);

        // Set up test order item
        testOrderItem = new OrderItem();
        testOrderItem.setId(1L);
        testOrderItem.setBook(testBook);
        testOrderItem.setQuantity(1);
        testOrderItem.setPriceAtPurchase(29.99);

        // Set up test order
        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setUser(testUser);
        testOrder.setOrderDate(LocalDateTime.now());
        testOrder.setPaymentMethod(PaymentMethod.WEB);
        testOrder.setStatus(OrderStatus.PENDING);
        testOrder.setTotalAmount(29.99);
        testOrder.setItems(Arrays.asList(testOrderItem));

        // Set up checkout request DTO
        testCheckoutRequest = new CheckoutRequestDTO();
        testCheckoutRequest.setPaymentMethod(PaymentMethod.WEB);
    }

    @Test
    void createOrder_ShouldCreateNewOrder() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(cartService.getCartItems(1L)).thenReturn(Arrays.asList(testCartItem));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        OrderResponseDTO result = orderService.createOrder(1L, testCheckoutRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getUserId());
        assertEquals("WEB", result.getPaymentMethod());
        assertEquals("PENDING", result.getStatus());
        verify(cartService).clearCart(1L);
    }

    @Test
    void createOrder_ShouldThrowException_WhenCartEmpty() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(cartService.getCartItems(1L)).thenReturn(List.of());

        assertThrows(RuntimeException.class, () -> orderService.createOrder(1L, testCheckoutRequest));
    }

    @Test
    void getUserOrders_ShouldReturnUserOrders() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(orderRepository.findByUserOrderByOrderDateDesc(testUser))
                .thenReturn(Arrays.asList(testOrder));

        List<OrderResponseDTO> result = orderService.getUserOrders(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(1L, result.get(0).getUserId());
    }

    @Test
    void processPayment_ShouldUpdateOrderStatus() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            savedOrder.setStatus(OrderStatus.PAID);
            return savedOrder;
        });

        OrderResponseDTO result = orderService.processPayment(1L);

        assertNotNull(result);
        assertEquals("PAID", result.getStatus());
    }
}