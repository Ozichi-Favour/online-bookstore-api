package com.example.online.bookstore.controller;

import com.example.online.bookstore.dto.CheckoutRequestDTO;
import com.example.online.bookstore.dto.OrderResponseDTO;
import com.example.online.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<OrderResponseDTO> createOrder(
            @PathVariable Long userId,
            @RequestBody CheckoutRequestDTO checkoutRequest) {
        return ResponseEntity.ok(orderService.createOrder(userId, checkoutRequest));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }

    @PostMapping("/{orderId}/process-payment")
    public ResponseEntity<OrderResponseDTO> processPayment(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.processPayment(orderId));
    }
}