package com.example.orders.service;

import com.example.orders.entity.Order;
import com.example.orders.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Create order
    public Order createOrder(Order order) {

        if (order.getStatus() == null || order.getStatus().isBlank()) {
            order.setStatus("NEW");
        }

        return orderRepository.save(order);
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get order by ID
    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    // Update order
    public Order updateOrder(String id, Order orderDetails) {

        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found with id: " + id));

        existingOrder.setCustomerName(orderDetails.getCustomerName());
        existingOrder.setProductName(orderDetails.getProductName());
        existingOrder.setQuantity(orderDetails.getQuantity());
        existingOrder.setPrice(orderDetails.getPrice());
        existingOrder.setStatus(orderDetails.getStatus());

        return orderRepository.save(existingOrder);
    }

    // Delete order
    public void deleteOrder(String id) {

        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }

        orderRepository.deleteById(id);
    }
}