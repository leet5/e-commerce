package com.leet5.ecommerce.service;

import com.leet5.ecommerce.model.dto.OrderDTO;
import com.leet5.ecommerce.model.dto.OrderRequest;

import java.util.List;

public interface OrderService {
    OrderDTO placeOrder(OrderRequest orderRequest);

    OrderDTO getOrderById(Long orderId);

    List<OrderDTO> getAllOrders(int page, int size);

    void deleteOrderById(Long orderId);

    OrderDTO updateOrder(Long id, OrderDTO order);

    int getVersion();
}
