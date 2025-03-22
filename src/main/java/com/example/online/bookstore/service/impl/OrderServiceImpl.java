package com.example.online.bookstore.service.impl;

import com.example.online.bookstore.dto.CheckoutRequestDTO;
import com.example.online.bookstore.dto.OrderItemDTO;
import com.example.online.bookstore.dto.OrderResponseDTO;
import com.example.online.bookstore.enums.OrderStatus;
import com.example.online.bookstore.enums.PaymentMethod;
import com.example.online.bookstore.model.CartItem;
import com.example.online.bookstore.model.Order;
import com.example.online.bookstore.model.OrderItem;
import com.example.online.bookstore.model.User;
import com.example.online.bookstore.repository.OrderRepository;
import com.example.online.bookstore.repository.UserRepository;
import com.example.online.bookstore.service.CartService;
import com.example.online.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

    @Override
    @Transactional
    public OrderResponseDTO createOrder(Long userId, CheckoutRequestDTO checkoutRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartService.getCartItems(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Convert the string payment method to the enum value
        PaymentMethod paymentMethod = checkoutRequest.getPaymentMethod();
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentMethod(paymentMethod);
        order.setStatus(OrderStatus.PENDING);

        // Create OrderItem instances from CartItems
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setOrder(order);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getBook().getPrice());
            order.addItem(orderItem); // Assuming you've added the helper method to Order
        }

        order.setTotalAmount(calculateTotal(cartItems));

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(userId);

        return mapToOrderResponseDTO(savedOrder);
    }
    @Override
    public List<OrderResponseDTO> getUserOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUserOrderByOrderDateDesc(user);
        return orders.stream()
                .map(this::mapToOrderResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponseDTO processPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Simulate payment processing
        order.setStatus(OrderStatus.PAID);
        Order updatedOrder = orderRepository.save(order);

        return mapToOrderResponseDTO(updatedOrder);
    }

    private Double calculateTotal(List<CartItem> cartItems) {
        return cartItems.stream()
                .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
                .sum();
    }

    // Mapper methods
    private OrderResponseDTO mapToOrderResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setPaymentMethod(order.getPaymentMethod().name());
        dto.setStatus(order.getStatus().name());
        dto.setTotalAmount(order.getTotalAmount());

        List<OrderItemDTO> itemDTOs = order.getItems().stream()
                .map(this::mapToOrderItemDTO)
                .collect(Collectors.toList());
        dto.setItems(itemDTOs);

        return dto;
    }

    private OrderItemDTO mapToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(orderItem.getId());
        dto.setBookId(orderItem.getBook().getId());
        dto.setBookTitle(orderItem.getBook().getTitle());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPriceAtPurchase(orderItem.getPriceAtPurchase());
        return dto;
    }
}