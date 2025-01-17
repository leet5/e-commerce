package com.leet5.ecommerce.service;

import com.leet5.ecommerce.model.entity.Customer;
import com.leet5.ecommerce.model.entity.Order;
import com.leet5.ecommerce.model.entity.Payment;
import com.leet5.ecommerce.model.entity.Shipment;
import com.leet5.ecommerce.model.vo.PaymentMethod;
import com.leet5.ecommerce.model.vo.ShipmentStatus;
import com.leet5.ecommerce.repository.CustomerRepository;
import com.leet5.ecommerce.repository.OrderRepository;
import com.leet5.ecommerce.repository.PaymentRepository;
import com.leet5.ecommerce.repository.ShipmentRepository;
import com.leet5.ecommerce.service.implementation.ShipmentServiceV1Impl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
public class ShipmentServiceTest {
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PaymentRepository paymentRepository;


    private ShipmentService shipmentService;

    @BeforeEach
    void setUp() {
        shipmentService = new ShipmentServiceV1Impl(shipmentRepository, orderRepository);
    }

    @Test
    public void simulateShipmentTracking() {
        final Order order = createMockOrder();
        orderRepository.save(order);

        String trackingNumber = "XYZ123456789";
        ShipmentStatus status = ShipmentStatus.DELIVERED;

        shipmentService.trackShipment(order, trackingNumber, status);

        final Shipment shipment = shipmentRepository.findByOrderId(order.getId());
        assertEquals(shipment.getTrackingNumber(), trackingNumber);
        assertEquals(shipment.getStatus(), status);
        assertNotNull(shipment.getShipmentDate());
    }

    private Order createMockOrder() {
        final Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setBirthdate(LocalDate.of(2023, Month.OCTOBER, 26));
        customerRepository.save(customer);

        final Payment payment = new Payment();
        payment.setPaymentDateTime(LocalDateTime.of(2020, Month.JANUARY, 1, 10, 20, 30));
        payment.setPaymentMethod(PaymentMethod.CASH);
        payment.setAmount(BigDecimal.valueOf(27.26));
        paymentRepository.save(payment);

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDateTime(LocalDateTime.of(2023, Month.OCTOBER, 10, 12, 30, 25));
        order.setTotalAmount(BigDecimal.TEN);
        order.setPayment(payment);
        return orderRepository.save(order);
    }
}
