package com.example.online.bookstore.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Integer quantity;
    private Double priceAtPurchase;
}
