package com.leet5.ecommerce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "products_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    @NotNull(message = "Price must be specified")
    @PositiveOrZero(message = "Price must be zero or positive")
    private BigDecimal price;

    @Column(name = "stock_quantity", nullable = false)
    @NotNull(message = "Stock quantity must be specified")
    @PositiveOrZero(message = "Stock quantity must be zero or positive")
    private Integer stockQuantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public @NotNull(message = "Price must be specified") @PositiveOrZero(message = "Price must be zero or positive") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Price must be specified") @PositiveOrZero(message = "Price must be zero or positive") BigDecimal price) {
        this.price = price;
    }

    public @NotNull(message = "Stock quantity must be specified") @PositiveOrZero(message = "Stock quantity must be zero or positive") Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(@NotNull(message = "Stock quantity must be specified") @PositiveOrZero(message = "Stock quantity must be zero or positive") Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
