package com.leet5.ecommerce.service;

import com.leet5.ecommerce.model.entity.Order;
import com.leet5.ecommerce.model.vo.ShipmentStatus;

public interface ShipmentService {
    void trackShipment(Order order, String trackingNumber, ShipmentStatus shipmentStatus);

    int getVersion();
}
