package com.example.online.bookstore.service;

import com.example.online.bookstore.dto.CheckoutRequestDTO;
import com.example.online.bookstore.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(Long userId, CheckoutRequestDTO checkoutRequest);
    List<OrderResponseDTO> getUserOrders(Long userId);
    OrderResponseDTO processPayment(Long orderId);
}