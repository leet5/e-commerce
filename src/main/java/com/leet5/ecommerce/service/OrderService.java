package com.leet5.ecommerce.service;

import com.leet5.ecommerce.model.dto.OrderRequest;
import com.leet5.ecommerce.model.entity.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(OrderRequest orderRequest);

    Order getOrderById(Long orderId);

    List<Order> getAllOrders();
}
