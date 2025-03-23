package com.example.online.bookstore.controller;


import com.example.online.bookstore.dto.CheckoutRequestDTO;
import com.example.online.bookstore.dto.OrderResponseDTO;
import com.example.online.bookstore.enums.PaymentMethod;
import com.example.online.bookstore.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private OrderResponseDTO testOrder;

    @BeforeEach
    void setUp() {
        testOrder = new OrderResponseDTO();
        testOrder.setId(1L);
        testOrder.setTotalAmount(29.99);
    }

    @Test
    void createOrder_ShouldReturnOrder() throws Exception {
        CheckoutRequestDTO checkoutRequest = new CheckoutRequestDTO();
        checkoutRequest.setPaymentMethod(PaymentMethod.WEB);
        when(orderService.createOrder(eq(1L), any(CheckoutRequestDTO.class)))
                .thenReturn(testOrder);

        mockMvc.perform(post("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"paymentMethod\":\"WEB\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.totalAmount").value(29.99));
    }

    @Test
    void getUserOrders_ShouldReturnOrders() throws Exception {
        when(orderService.getUserOrders(1L))
                .thenReturn(Arrays.asList(testOrder));

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].totalAmount").value(29.99));
    }

    @Test
    void processPayment_ShouldReturnUpdatedOrder() throws Exception {
        when(orderService.processPayment(1L))
                .thenReturn(testOrder);

        mockMvc.perform(post("/api/orders/1/process-payment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.totalAmount").value(29.99));
    }
}