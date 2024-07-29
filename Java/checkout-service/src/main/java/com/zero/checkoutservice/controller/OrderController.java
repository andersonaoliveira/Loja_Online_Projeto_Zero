package com.zero.checkoutservice.controller;

import com.zero.checkoutservice.model.Order;
import com.zero.checkoutservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkout")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<Order>> getOrders(@PathVariable String userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId).orElseThrow(() -> new RuntimeException("Order not found")));
    }

    @PostMapping("/{userId}/order")
    public ResponseEntity<Order> createOrder(@PathVariable String userId, @RequestBody Order order) {
        order.setUserId(userId);
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @PutMapping("/order/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable String orderId, @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
