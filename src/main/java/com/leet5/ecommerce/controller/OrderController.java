package com.leet5.ecommerce.controller;

import com.leet5.ecommerce.model.dto.OrderDTO;
import com.leet5.ecommerce.model.dto.OrderRequest;
import com.leet5.ecommerce.service.factory.OrderServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderServiceFactory orderServiceFactory;

    @Autowired
    public OrderController(OrderServiceFactory orderServiceFactory) {
        this.orderServiceFactory = orderServiceFactory;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody OrderRequest orderRequest,
                                               @RequestHeader("api-version") int apiVersion) {
        final var orderService = orderServiceFactory.getService(apiVersion);
        final var order = orderService.placeOrder(orderRequest);
        return ResponseEntity.created(URI.create("/orders" + order.id())).body(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id,
                                                 @RequestHeader("api-version") int apiVersion) {
        final var orderService = orderServiceFactory.getService(apiVersion);
        final var order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "20") int size,
                                                       @RequestHeader("api-version") int apiVersion) {
        final var orderService = orderServiceFactory.getService(apiVersion);
        final var orders = orderService.getAllOrders(page, size);
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long id,
                                                @RequestHeader("api-version") int apiVersion) {
        final var orderService = orderServiceFactory.getService(apiVersion);
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id,
                                                @RequestBody OrderDTO orderDTO,
                                                @RequestHeader("api-version") int apiVersion) {
        final var orderService = orderServiceFactory.getService(apiVersion);
        final var result = orderService.updateOrder(id, orderDTO);
        return ResponseEntity.ok(result);
    }
}
