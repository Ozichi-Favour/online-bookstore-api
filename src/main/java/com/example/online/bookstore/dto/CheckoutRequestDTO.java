package com.example.online.bookstore.dto;

import com.example.online.bookstore.enums.PaymentMethod;
import lombok.Data;

@Data
public class CheckoutRequestDTO {
    private PaymentMethod paymentMethod;
}