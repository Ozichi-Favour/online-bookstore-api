package com.example.online.bookstore.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long id;
    private Long userId;
    private List<OrderItemDTO> items;
    private LocalDateTime orderDate;
    private String paymentMethod;  // Verify this exists
    private String status;
    private Double totalAmount;
}