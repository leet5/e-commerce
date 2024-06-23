package com.leet5.ecommerce.controller;

import com.leet5.ecommerce.model.dto.OrderDTO;
import com.leet5.ecommerce.model.dto.OrderItemRequest;
import com.leet5.ecommerce.model.dto.OrderRequest;
import com.leet5.ecommerce.service.factory.OrderServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.leet5.ecommerce.util.ApiConstants.API_VERSION_HEADER;

@RestController
@RequestMapping("/customers/{customerId}/orders")
public class OrderController {
    private final OrderServiceFactory orderServiceFactory;

    @Autowired
    public OrderController(OrderServiceFactory orderServiceFactory) {
        this.orderServiceFactory = orderServiceFactory;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> placeOrder(@PathVariable long customerId,
                                               @RequestBody List<OrderItemRequest> items,
                                               @RequestHeader(API_VERSION_HEADER) int apiVersion) {
        final var orderService = orderServiceFactory.getService(apiVersion);
        final var order = orderService.placeOrder(new OrderRequest(customerId, items));
        return ResponseEntity.created(URI.create("/orders" + order.id())).body(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable long customerId,
                                                 @PathVariable long id,
                                                 @RequestHeader(API_VERSION_HEADER) int apiVersion) {
        final var orderService = orderServiceFactory.getService(apiVersion);
        final var order = orderService.getOrderById(id);
        if (!order.id().equals(customerId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders(@PathVariable long customerId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "20") int size,
                                                       @RequestHeader(API_VERSION_HEADER) int apiVersion) {
        final var orderService = orderServiceFactory.getService(apiVersion);
        final var orders = orderService.getAllOrdersByCustomerId(customerId, page, size);
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable long customerId,
                                                @PathVariable long id,
                                                @RequestHeader(API_VERSION_HEADER) int apiVersion) {
        final var orderService = orderServiceFactory.getService(apiVersion);
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable long customerId,
                                                @PathVariable long orderId,
                                                @RequestBody OrderDTO orderDTO,
                                                @RequestHeader(API_VERSION_HEADER) int apiVersion) {
        final var orderService = orderServiceFactory.getService(apiVersion);
        final var result = orderService.updateOrder(orderId, orderDTO);
        return ResponseEntity.ok(result);
    }
}
