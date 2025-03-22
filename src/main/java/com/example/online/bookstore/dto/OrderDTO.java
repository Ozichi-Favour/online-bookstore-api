package com.example.online.bookstore.dto;

import com.example.online.bookstore.enums.PaymentMethod;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private List<OrderItemDTO> items;
    private LocalDateTime orderDate;
    private PaymentMethod paymentMethod;
    private String status;
    private Double totalAmount;
}