package com.leet5.ecommerce.service;

import com.leet5.ecommerce.model.dto.OrderDTO;
import com.leet5.ecommerce.model.dto.OrderRequest;

import java.util.List;

public interface OrderService {
    OrderDTO placeOrder(OrderRequest orderRequest);

    OrderDTO getOrderById(long orderId);

    List<OrderDTO> getAllOrdersByCustomerId(long customerId, int page, int size);

    void deleteOrderById(long orderId);

    OrderDTO updateOrder(long orderId, OrderDTO order);

    int getVersion();
}
