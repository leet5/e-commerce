package com.leet5.ecommerce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq")
    @SequenceGenerator(name = "order_item_seq", sequenceName = "order_items_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull(message = "Product must be specified")
    private Product product;

    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = "Quantity must be greater than or equal to 1")
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    @NotNull(message = "Unit price must be specified")
    @Positive(message = "Unit price must be positive")
    private BigDecimal unitPrice;

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

    public @NotNull(message = "Product must be specified") Product getProduct() {
        return product;
    }

    public void setProduct(@NotNull(message = "Product must be specified") Product product) {
        this.product = product;
    }

    @Min(value = 1, message = "Quantity must be greater than or equal to 1")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(@Min(value = 1, message = "Quantity must be greater than or equal to 1") int quantity) {
        this.quantity = quantity;
    }

    public @NotNull(message = "Unit price must be specified") @Positive(message = "Unit price must be positive") BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(@NotNull(message = "Unit price must be specified") @Positive(message = "Unit price must be positive") BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
