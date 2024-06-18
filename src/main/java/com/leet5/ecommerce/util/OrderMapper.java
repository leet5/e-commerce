package com.leet5.ecommerce.util;

import com.leet5.ecommerce.model.dto.OrderDTO;
import com.leet5.ecommerce.model.entity.Order;
import com.leet5.ecommerce.model.entity.OrderItem;

public class OrderMapper {
    public static OrderDTO toOrderDTO(Order order) {
        final Long payment = order.getPayment() == null ? null : order.getPayment().getId();
        final Long shipment = order.getShipment() == null ? null : order.getShipment().getId();

        return new OrderDTO(
                order.getId(),
                order.getCustomer().getId(),
                order.getOrderItems().stream().map(OrderItem::getId).toList(),
                order.getOrderDateTime(),
                order.getTotalAmount(),
                payment,
                shipment
        );
    }
}
