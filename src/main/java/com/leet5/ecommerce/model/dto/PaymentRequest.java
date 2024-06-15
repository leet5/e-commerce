package com.leet5.ecommerce.model.dto;

import com.leet5.ecommerce.model.vo.PaymentMethod;

public record PaymentRequest(Long orderId, PaymentMethod paymentMethod) {
}
