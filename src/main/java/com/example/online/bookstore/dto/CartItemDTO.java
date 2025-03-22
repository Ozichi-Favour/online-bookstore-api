package com.example.online.bookstore.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long bookId;
    private Integer quantity;
}
