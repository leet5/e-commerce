package com.leet5.ecommerce.model.entity;

import com.leet5.ecommerce.model.vo.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "amount", nullable = false)
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    @NotNull(message = "Payment date and time must be specified")
    private LocalDateTime paymentDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    @NotNull(message = "Payment method must be specified")
    private PaymentMethod paymentMethod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Positive(message = "Amount must be positive") BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@Positive(message = "Amount must be positive") BigDecimal amount) {
        this.amount = amount;
    }

    public @NotNull(message = "Payment date and time must be specified") LocalDateTime getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(@NotNull(message = "Payment date and time must be specified") LocalDateTime paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public @NotNull(message = "Payment method must be specified") PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(@NotNull(message = "Payment method must be specified") PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
