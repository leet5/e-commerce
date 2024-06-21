package com.leet5.ecommerce.controller;

import com.leet5.ecommerce.model.dto.PaymentRequest;
import com.leet5.ecommerce.model.entity.Payment;
import com.leet5.ecommerce.service.factory.PaymentServiceFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentServiceFactory paymentServiceFactory;

    public PaymentController(PaymentServiceFactory paymentServiceFactory) {
        this.paymentServiceFactory = paymentServiceFactory;
    }

    @PostMapping
    public ResponseEntity<Payment> processPayment(@RequestBody PaymentRequest request,
                                                  @RequestHeader("api-version") int apiVersion) {
        final var paymentService = paymentServiceFactory.getService(apiVersion);
        final var payment = paymentService.processPayment(request);
        return ResponseEntity.ok(payment);
    }
}
