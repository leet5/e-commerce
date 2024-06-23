package com.leet5.ecommerce.service.implementation;

import com.leet5.ecommerce.model.dto.OrderDTO;
import com.leet5.ecommerce.model.dto.OrderRequest;
import com.leet5.ecommerce.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderServiceV2Impl implements OrderService {
    private static final int VERSION = 2;

    @Override
    public OrderDTO placeOrder(OrderRequest orderRequest) {
        return null;
    }

    @Override
    public OrderDTO getOrderById(Long orderId) {
        return null;
    }

    @Override
    public List<OrderDTO> getAllOrders(int page, int size) {
        return List.of();
    }

    @Override
    public void deleteOrderById(Long orderId) {

    }

    @Override
    public OrderDTO updateOrder(Long id, OrderDTO order) {
        return null;
    }

    @Override
    public int getVersion() {
        return VERSION;
    }
}
