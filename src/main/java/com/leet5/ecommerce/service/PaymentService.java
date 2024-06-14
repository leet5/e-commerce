package com.leet5.ecommerce.service;

import com.leet5.ecommerce.model.entity.Order;
import com.leet5.ecommerce.model.entity.Payment;
import com.leet5.ecommerce.model.vo.PaymentMethod;
import com.leet5.ecommerce.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class PaymentService {
    private final static Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment processPayment(Order order, BigDecimal amount, PaymentMethod paymentMethod) {
        logger.info("Processing payment for order ID: {}", order.getId());

        try {
            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setAmount(amount);
            payment.setPaymentMethod(paymentMethod);
            payment.setPaymentDateTime(LocalDateTime.now());

            payment = paymentRepository.save(payment);

            order.setPayment(payment);
            logger.info("Payment processed successfully for order ID: {}", order.getId());

            return payment;
        } catch (Exception e) {
            logger.error("Error processing payment for order ID {}: {}", order.getId(), e.getMessage());
            throw e;
        }
    }
}
