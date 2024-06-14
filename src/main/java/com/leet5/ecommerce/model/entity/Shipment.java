package com.leet5.ecommerce.model.entity;

import com.leet5.ecommerce.model.vo.ShipmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    @NotNull(message = "Order must be specified")
    private Order order;

    @Column(name = "shipment_date")
    @PastOrPresent(message = "Shipment date must be in the past or present")
    private LocalDateTime shipmentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ShipmentStatus status;

    @Column(name = "tracking_number")
    @Size(max = 50, message = "Tracking number must not exceed 50 characters")
    private String trackingNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Order must be specified") Order getOrder() {
        return order;
    }

    public void setOrder(@NotNull(message = "Order must be specified") Order order) {
        this.order = order;
    }

    public @PastOrPresent(message = "Shipment date must be in the past or present") LocalDateTime getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(@PastOrPresent(message = "Shipment date must be in the past or present") LocalDateTime shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public @Size(max = 50, message = "Tracking number must not exceed 50 characters") String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(@Size(max = 50, message = "Tracking number must not exceed 50 characters") String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}
