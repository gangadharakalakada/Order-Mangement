package com.example.orders.controller;

import com.example.orders.entity.Order;
import com.example.orders.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestBody Order order) {

        Order createdOrder = orderService.createOrder(order);

        return new ResponseEntity<>(
                createdOrder,
                HttpStatus.CREATED
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {

        List<Order> orders = orderService.getAllOrders();

        return ResponseEntity.ok(orders);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(
            @PathVariable Long id) {

        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable Long id,
            @RequestBody Order order) {

        try {

            Order updatedOrder =
                    orderService.updateOrder(id, order);

            return ResponseEntity.ok(updatedOrder);

        } catch (RuntimeException exception) {

            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable Long id) {

        try {

            orderService.deleteOrder(id);

            return ResponseEntity.noContent().build();

        } catch (RuntimeException exception) {

            return ResponseEntity.notFound().build();
        }
    }
}