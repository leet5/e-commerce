package com.leet5.ecommerce.controller;

import com.leet5.ecommerce.model.dto.OrderRequest;
import com.leet5.ecommerce.model.entity.Order;
import com.leet5.ecommerce.service.OrderService;
import com.leet5.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.leet5.ecommerce.util.ApiConstants.API_VERSION_1;

@RestController
@RequestMapping(API_VERSION_1 + "/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest) {
        final Order order = orderService.placeOrder(orderRequest);
        return ResponseEntity.created(URI.create(API_VERSION_1 + "/orders" + order.getId())).body(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        final Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        final List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
