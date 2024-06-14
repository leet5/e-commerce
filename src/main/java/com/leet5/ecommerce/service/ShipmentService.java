package com.leet5.ecommerce.service;

import com.leet5.ecommerce.model.entity.Order;
import com.leet5.ecommerce.model.entity.Shipment;
import com.leet5.ecommerce.model.vo.ShipmentStatus;
import com.leet5.ecommerce.repository.OrderRepository;
import com.leet5.ecommerce.repository.ShipmentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class ShipmentService {
    private static final Logger logger = LoggerFactory.getLogger(ShipmentService.class);

    private final ShipmentRepository shipmentRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public ShipmentService(ShipmentRepository shipmentRepository, OrderRepository orderRepository) {
        this.shipmentRepository = shipmentRepository;
        this.orderRepository = orderRepository;
    }

    public void trackShipment(Order order, String trackingNumber, ShipmentStatus shipmentStatus) {
        logger.info("Tracking shipment for order ID: {}", order.getId());

        try {
            Shipment shipment = new Shipment();
            shipment.setOrder(order);
            shipment.setShipmentDate(LocalDateTime.now());
            shipment.setStatus(shipmentStatus);
            shipment.setTrackingNumber(trackingNumber);

            shipment = shipmentRepository.save(shipment);

            order.setShipment(shipment);
            orderRepository.save(order);

            logger.info("Shipment tracked successfully for order ID: {}", order.getId());
        } catch (Exception e) {
            logger.error("Error tracking shipment for order ID {}: {}", order.getId(), e.getMessage());
            throw e;
        }
    }
}
