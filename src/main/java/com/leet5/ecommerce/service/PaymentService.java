package com.leet5.ecommerce.service;

import com.leet5.ecommerce.model.dto.PaymentRequest;
import com.leet5.ecommerce.model.entity.Payment;

public interface PaymentService {
    Payment processPayment(PaymentRequest paymentRequest);
}
