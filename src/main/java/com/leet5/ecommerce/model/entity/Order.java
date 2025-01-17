package com.leet5.ecommerce.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "orders_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Valid
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "order_date", nullable = false)
    @NotNull(message = "Order date and time must be specified")
    private LocalDateTime orderDateTime;

    @Column(name = "total_amount", nullable = false)
    @NotNull(message = "Total amount must be specified")
    private BigDecimal totalAmount;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Customer must be specified") Customer getCustomer() {
        return customer;
    }

    public void setCustomer(@NotNull(message = "Customer must be specified") Customer customer) {
        this.customer = customer;
    }

    public @Valid List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(@Valid List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public @NotNull(message = "Order date and time must be specified") LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(@NotNull(message = "Order date and time must be specified") LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public @NotNull(message = "Total amount must be specified") BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(@NotNull(message = "Total amount must be specified") BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }
}
