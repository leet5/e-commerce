package com.leet5.ecommerce.service.implementation;

import com.leet5.ecommerce.exception.order.OrderNotFoundException;
import com.leet5.ecommerce.model.dto.PaymentRequest;
import com.leet5.ecommerce.model.entity.Order;
import com.leet5.ecommerce.model.entity.Payment;
import com.leet5.ecommerce.repository.OrderRepository;
import com.leet5.ecommerce.repository.PaymentRepository;
import com.leet5.ecommerce.service.PaymentService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class PaymentServiceV1Impl implements PaymentService {
    private final static Logger log = LoggerFactory.getLogger(PaymentServiceV1Impl.class);
    private static final int VERSION = 1;

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public PaymentServiceV1Impl(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Payment processPayment(PaymentRequest paymentRequest) {
        log.info("Processing payment request: {}", paymentRequest);

        final Order order = orderRepository
                .findById(paymentRequest.orderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + paymentRequest.orderId()));

        Payment payment = new Payment();
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(paymentRequest.paymentMethod());
        payment.setPaymentDateTime(LocalDateTime.now());

        payment = paymentRepository.save(payment);

        order.setPayment(payment);
        orderRepository.save(order);
        log.info("Payment processed successfully for order ID: {}", order.getId());

        return payment;
    }

    @Override
    public int getVersion() {
        return VERSION;
    }
}
