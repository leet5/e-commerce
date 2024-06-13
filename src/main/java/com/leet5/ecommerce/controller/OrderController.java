package com.leet5.ecommerce.controller;

import com.leet5.ecommerce.model.dto.OrderRequest;
import com.leet5.ecommerce.model.entity.Order;
import com.leet5.ecommerce.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        logger.info("Received order creation request");

        try {
            final Order order = orderService.placeOrder(orderRequest);
            logger.info("Order placed successfully with ID: {}", order.getId());
            return ResponseEntity.created(URI.create("/api/orders" + order.getId())).body(order);
        } catch (Exception e) {
            logger.error("Error creating order: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        logger.info("Fetching order details for order ID: {}", orderId);

        try {
            final Optional<Order> order = orderService.getOrderById(orderId);
            return order.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error fetching order details for ID {}: {}", orderId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        logger.info("Received request for all orders");

        try {
            final List<Order> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            logger.error("Error fetching all orders: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
